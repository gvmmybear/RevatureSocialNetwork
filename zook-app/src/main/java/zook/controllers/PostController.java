package zook.controllers;



import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import zook.model.Post;
import zook.model.User;
import zook.services.PostServices;

@RestController
public class PostController {

	///////field 
	// postDao
	
	@Autowired
	PostServices postServ = new PostServices();
	
	//////endpoints 
	@CrossOrigin
	@PostMapping("/addPost")				 
	public boolean addPost(@RequestBody Post post) {
		boolean posted = false;
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
//		Date date=ts;
		
		post.setPostTimeStamp(ts);
		
		if(postServ.addPost(post)) {
			posted = true;
		}else {
			System.out.println("failed");
		}
		
		return posted;
	}
	
	@PostMapping("/likePost")						
							//as of now takes a whole post but with a constructor of just ID should be just fine 
	public @ResponseBody int likePost(@RequestBody Post post) {
		boolean liked =false;
		int postLikes =0;
		System.out.println(post);
		Post currentUserPost = null;
		currentUserPost= postServ.likePost(post);
		if(!(currentUserPost==null)) {
			liked =true;
			postLikes =currentUserPost.getPostLikes();
		}else {
			System.out.println("Failed");
		}
		System.out.println(postLikes);
		return postLikes;
		
	}
	
	@PostMapping("/seeAllPost")
	public @ResponseBody List<Post> allPost(){
		
		
		return postServ.seeAllPosts();
	}
	
	@PostMapping("/seeAllPostByUser")
	public List<Post> allPostByUser(@RequestBody User user){
		
		
		return postServ.seeAllPostsByUser(user);
	}
	
	
}
