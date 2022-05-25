package zook.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;

@RestController
@RequestMapping("/documents")
public class ImageController {
	@Value("${document.bucket-name}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;

//    @DeleteMapping("/{fileName}")
//    public ResponseEntity deleteDocument(@PathVariable String fileName) {
//        
//        amazonS3.deleteObject(bucketName, fileName);
//        return ResponseEntity.ok().build();
//    }

    @CrossOrigin
    @GetMapping("/getall")
    public List<String> getAllDocuments() {
    	System.out.println("Test");
        return amazonS3.listObjectsV2(bucketName).getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @PostMapping("/upload")
    public String uploadDocument(@RequestParam(value = "file")MultipartFile file) throws IOException {
    	System.out.println("in the upload section");
    	String tempFileName = UUID.randomUUID() + ".gif";
    	System.out.println(tempFileName);
        
        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + tempFileName);
        System.out.println("Made it past creating the temp file");
        ((MultipartFile) file).transferTo(tempFile);
        System.out.println("Made it past transferring file to the temp file");
        amazonS3.putObject(bucketName, tempFileName, tempFile);
        System.out.println("Made it past amazonS3 putObject");
        tempFile.deleteOnExit();
        return tempFileName;

    }
    

    @CrossOrigin
    @GetMapping("/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) throws IOException {
    	
        S3Object data = amazonS3.getObject(bucketName, fileName);
        S3ObjectInputStream objectContent = data.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectContent);
        ByteArrayResource resource = new ByteArrayResource(bytes);
        objectContent.close();
        return ResponseEntity
                .ok()
                .contentLength(bytes.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @CrossOrigin
    @GetMapping("/presigned-url/{fileName}")
    public String presignedUrl(@PathVariable String fileName) throws IOException {
    	System.out.println("\n\n"+fileName+"\n\n");
        return amazonS3.generatePresignedUrl(bucketName, fileName, convertToDateViaInstant(LocalDate.now().plusDays(1))).toString();

    }

    private Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

}
