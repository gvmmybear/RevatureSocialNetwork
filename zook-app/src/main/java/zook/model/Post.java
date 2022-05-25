package zook.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.boot.jackson.JsonObjectDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@AllArgsConstructor  // i commented these out cause I was getting an error
//@NoArgsConstructor
@Entity
//@Data
@Table(name = "Post")
public class Post {

	@Id
	@Column(name="post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postID;
	
	
	@Column(name= "post_decrip", nullable = false)
	private String postDescrip;
	
	@Column(name="post_timestamp", nullable = false)
	private Timestamp postTimeStamp;
	
	@Column(name = "post_likes", nullable = false)
	private int postLikes;
	
	@Column(name="post_image", nullable = true)
	private String postImage;
	
	@Column(name="post_youtube", nullable = true)
	private String postYoutube; 
	
//	@Column(name = "post_owner", nullable = false)
	@ManyToOne(cascade=CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User postOwner;
	
	/////again had to hardcode this out because lombok isnt working for me 
	public Post() {
		super();
	}
	
	//allArgs construct... for liking a post youll need this 
	public Post(int postID, User postOwner, String postDescrip, Timestamp postTimeStamp, int postLikes) {
		super();
		this.postID = postID;
		this.postOwner = postOwner;
		this.postDescrip = postDescrip;
		this.postTimeStamp = postTimeStamp;
		this.postLikes = postLikes;
	}
	
	
	
	////Constructor without ID, I guess we hardcode each post to start with 0 likes?
	public Post(User postOwner, String postDescrip, Timestamp postTimeStamp, int postLikes) {
		super();
		this.postOwner = postOwner;
		this.postDescrip = postDescrip;
		this.postTimeStamp = postTimeStamp;
		this.postLikes = postLikes;
	}
	
	//Constructor with postImage, for creating a post
	public Post(User postOwner, String postDescrip, Timestamp postTimeStamp, String postImage) {
		super();
		this.postOwner = postOwner;
		this.postDescrip = postDescrip;
		this.postTimeStamp = postTimeStamp;
		this.postImage = postImage;
		
		postLikes = 0;
	}

//	public JSONObject getJsonobj() {
//		JsonObject 
//	}

	public int getPostID() {
		return postID;
	}


	public void setPostID(int postID) {
		this.postID = postID;
	}


	public User getPostOwner() {
		return postOwner;
	}


	public void setPostOwner(User postOwner) {
		this.postOwner = postOwner;
	}


	public String getPostDescrip() {
		return postDescrip;
	}


	public void setPostDescrip(String postDescrip) {
		this.postDescrip = postDescrip;
	}


	public Timestamp getPostTimeStamp() {
		return postTimeStamp;
	}


	public void setPostTimeStamp(Timestamp postTimeStamp) {
		this.postTimeStamp = postTimeStamp;
	}


	public int getPostLikes() {
		return postLikes;
	}


	public void setPostLikes(int postLikes) {
		this.postLikes = postLikes;
	}

	
	public String getPostImage() {
		return postImage;
	}

	public void setPostImage(String postImage) {
		this.postImage = postImage;
	}

	public String getPostYoutube() {
		return postYoutube;
	}

	public void setPostYoutube(String postYoutube) {
		this.postYoutube = postYoutube;
	}

	@Override
	public String toString() {
		return "Post [postID=" + postID + ", postDescrip=" + postDescrip + ", postTimeStamp=" + postTimeStamp
				+ ", postLikes=" + postLikes + ", postImage=" + postImage + ", postYoutube=" + postYoutube
				 + "]";
	}

	public Post(String postDescrip, String postImage, User postOwner) {
		super();
		this.postDescrip = postDescrip;
		this.postImage = postImage;
		this.postOwner = postOwner;
	}

}
