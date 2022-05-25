// Launch URL
const url = "http://ec2-54-241-44-71.us-west-1.compute.amazonaws.com:9039";
// Dev URL
// const url = "http://localhost:9039";




 window.onload = function () {
     console.log("window onload working");
    uploadButton.addEventListener('click', sendImage);

 }



let uploadButton = document.getElementById('uploadPicture');


const image = document.createElement('img');

image.src = 'https://project2-air-benders.s3.amazonaws.com/79ed2d2e-5c8c-448a-bba7-1b1ad001a244.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220512T145349Z&X-Amz-SignedHeaders=host&X-Amz-Expires=47170&X-Amz-Credential=AKIASM6QMHDZH6M7NPCN%2F20220512%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=4a5b256a47da3125329cd672226ec230903ee8398b6392c5b400a02a030aa7df';


document.getElementById("image2").appendChild(image);








function sendImage() {

    console.log("in send image");

    let imageToSend= document.getElementById('input').files[0];
    var formData = new FormData();
    formData.append("file",imageToSend);

    let xhttp = new XMLHttpRequest();


    xhttp.onreadystatechange = function () {


        if (xhttp.readyState == 4 && xhttp.status == 200) {

            let filepath =(xhttp.responseText);
            console.log(filepath);
            
        }
    }

    xhttp.open('POST', `${url}/documents/upload`);



    // console.log(formData);
    xhttp.send(formData);



}
