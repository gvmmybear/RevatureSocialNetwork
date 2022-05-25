package zook.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zook.dao.PostDao;
import zook.dao.ProfanityDao;
import zook.model.Post;
import zook.model.Profanity;
import zook.model.User;

@Service
public class PostServices {

	@Autowired
	private PostDao postDao;
	
	@Autowired
	private ProfanityDao profanityDao;
	
	public boolean addPost(Post post) {
		boolean success= true;
		String message = "";
		//begin profanity filtering
		
		List<String> cantSayThat = new ArrayList<>();
		List<Profanity> badWordsList = new ArrayList<>();
		badWordsList = profanityDao.findAll();
		for (int i = 0; i < badWordsList.size(); i++) {
			String temp = badWordsList.get(i).getProfanity();
			cantSayThat.add(temp);
		}
		
		for (String s : cantSayThat) {
			if (post.getPostDescrip().toLowerCase().contains(s)){
				success = false;
				message = "Language";
			}
		}		
		
		
		if (success == true) {
			try {
				postDao.save(post);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				success = false;
				message = "There was a problem";
			} 
		}
		return success;
	}
	
	///so far this takes a WHOLE post object, so we're going to have to get the whole post from the HTML, which its already displaying the data in the feed
	public Post likePost(Post post) {
		boolean liked = true;
		int oldLike =0;
		
		//takes the web post ID as the parameter, and brings back the WHOLE post from the DB
		Post currentPost = postDao.findByPostID(post.getPostID());
		
		if (currentPost==null) {
			System.out.println("failed");
			liked = false;
			return currentPost;
		}else {
			//gets the old likes and increments it, and then updates
			oldLike = currentPost.getPostLikes();
			int newLike = ++oldLike;
			currentPost.setPostLikes(newLike);
			postDao.save(currentPost);
		}
		return currentPost;
	}

	public List<Post> seeAllPosts () {
		ArrayList<Post>allPosts = new ArrayList<Post>();
		
		allPosts  = (ArrayList<Post>) postDao.findAll();
		
		return allPosts;
		
	}
	
	public List<Post> seeAllPostsByUser(User user){
		ArrayList<Post> postsByUser = new ArrayList<>();
		postsByUser = postDao.findAllByPostOwner(user);
		System.out.println(postsByUser);
//		postsByUser = Collections.reverse(postsByUser);
		return postsByUser = postDao.findAllByPostOwner(user);
	}
}
