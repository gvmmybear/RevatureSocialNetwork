package zook.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;



//@NoArgsConstructor

//@Data
@Entity
@Table(name = "Users")
public class User {

	
	///////////this creates a Column in the USERS table and also makes UserId a self generating primary key
	@Id
	@Column(name= "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	//////this make the userEmail unrepeatable and NOT null
	@Column(name = "user_email",unique=true, nullable=false)
	private String userEmail;
	
	@Column(name = "user_password", nullable = false)
	private String userPassword;
	
	@Column(name = "user_Fname", nullable = false)
	private String userFName;
	
	@Column(name = "user_Lname", nullable = false)
	private String userLName;
	
	@Column(name = "species", nullable = false)
	private String userSpecies; 
	
	@Column(name = "user_bio", nullable = true)
	private String userBio;
	
	@Column(name="user_profile_pic", nullable = true)
	private String userProfilePic;
	
	
	
	
	@Column(name="posts", nullable = true)
	@OneToMany(mappedBy = "postID", fetch = FetchType.EAGER)
	private List<Post> posts= new ArrayList<Post>();
	
	public User (int userId, String userFName, String userLName, String userEmail, String userProfilePic, String userBio, String userSpecies) {
		super();
		this.userId = userId;
		this.userFName = userFName;
		this.userLName = userLName;
		this.userEmail = userEmail;
		this.userProfilePic = userProfilePic;
		this.userBio = userBio;
		this.userSpecies = userSpecies;
		
	}
	

	/////constructer for creating an account..... does not include ID
	public User(String userEmail, String userPassword, String userFName, String userLName, String userSpecies) {
		super();
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userFName = userFName;
		this.userLName = userLName;
		this.userSpecies = userSpecies;
	}
	
	public User(String userEmail, String userPassword) {
		super();
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}
	
	public User(int userID) {
		super();
		this.userId= userID;
	}
	
	public User() {
		super();
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFName() {
		return userFName;
	}

	public void setUserFName(String userFName) {
		this.userFName = userFName;
	}

	public String getUserLName() {
		return userLName;
	}

	public void setUserLName(String userLName) {
		this.userLName = userLName;
	}

	public String getUserSpecies() {
		return userSpecies;
	}

	public void setUserSpecies(String userSpecies) {
		this.userSpecies = userSpecies;
	}

	public String getUserBio() {
		return userBio;
	}


	public void setUserBio(String userBio) {
		this.userBio = userBio;
	}

	public String getUserProfilePic() {
		return userProfilePic;
	}


	public void setUserProfilePic(String userProfilePic) {
		this.userProfilePic = userProfilePic;
	}


	@Override
	public String toString() {
		return "User [userId=" + userId + ", userEmail=" + userEmail + ", userPassword=" + userPassword + ", userFName="
				+ userFName + ", userLName=" + userLName + ", userSpecies=" + userSpecies + "]";
	}


	
	
	

	
	
	
	////this works, idk why my lombok isnt generating this for some reason 
	
	
	
	
	
}
