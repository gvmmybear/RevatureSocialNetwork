package zook.services;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zook.dao.ProfanityDao;
import zook.dao.UserDao;
import zook.model.Profanity;
import zook.model.User;

//just copying from https://www.sourcecodeexamples.net/2021/08/spring-boot-project-with-controller.html  for how to set
//up service in a spring boot project

@Service
public class UserServices {
	@Autowired
	private EmailService emailService;

	@Autowired
	DataSource dataSource;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProfanityDao profanityDao;

	public String addUser(User user) {
		String success = "";
		boolean profanity = false;

		/// gets the plain text of the userPassword and hashes it before adding to the
		/// DB
		String secretStuff = hashPassword(user.getUserPassword());
		user.setUserPassword(secretStuff);

		//create a list of Strings instead of a list of Profanity objects
		List<String> cantSayThat = new ArrayList<>();
		List<Profanity> badWordsList = new ArrayList<>();
		badWordsList = profanityDao.findAll();
		for (int i = 0; i < badWordsList.size(); i++) {
			String temp = badWordsList.get(i).getProfanity();
			cantSayThat.add(temp);
		}

		//for every word on the profanity list, if any field contains that word, flip profanity to true, Success string to "Language" for the warning on the page
		for (String s : cantSayThat) {
			if (user.getUserFName().toLowerCase().contains(s) || user.getUserLName().toLowerCase().contains(s)
					|| user.getUserEmail().toLowerCase().contains(s)
					|| user.getUserSpecies().toLowerCase().contains(s)) {
				profanity = true;
				success = "Language";

			}
		}
		//if there's no profanity, try sending the welcome email, if that fails, don't create the account and failure message to "Bad Email"
		if (profanity == false) {
			try {
				try {
					emailService.sendWelcomeEmail(user.getUserEmail(), user.getUserFName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					success = "Bad Email";
				}
				//if success hasn't changed, then the user account was created successfully
				if (success == "") {

					userDao.save(user);
					success = "Success";
				}
				// invalid email throws exception but doesn't seem to crash the server!
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println(e);
				success = "Duplicate Email";
			}
		}
		System.out.println("returning " + success);
		return success;
	}

	public User loginVerification(User user) {
		User authroizedUser = new User();

		// gets the user with the matching email from the DB and uses that User to
		// compare passwords
		User user1 = userDao.findByUserEmail(user.getUserEmail());

		if (checkPassword(user.getUserPassword(), user1.getUserPassword())) {
			authroizedUser = user1;
		} else {
			authroizedUser = null;
		}

		return authroizedUser;
	}

	
	public String rehashPassword(String newPassword) {
		return hashPassword(newPassword);
	}


	private String hashPassword(String userPassword) {

		return BCrypt.hashpw(userPassword, BCrypt.gensalt());
	}

	private boolean checkPassword(String userPassword, String hashedPassword) {
		if (BCrypt.checkpw(userPassword, hashedPassword))
			return true;
		else
			return false;

	}
	
	public List<User> allUsers(){
		List<User> userList = new ArrayList<>(userDao.findAll());
		return userList;
		
	}
	
	public User updateProfile (User newUser) {

		User currentUser = userDao.findByUserId(newUser.getUserId());
		System.out.println(newUser);
		if (!(newUser.getUserFName()=="")) {
			System.out.println("changing fn");
			currentUser.setUserFName(newUser.getUserFName());
		} 
			
		if (!(newUser.getUserLName()=="")) {
			System.out.println("changing ln");
			currentUser.setUserLName(newUser.getUserLName());
		} 
		
		
		if (!(newUser.getUserBio()=="")) {
			System.out.println("changing bio");
			currentUser.setUserBio(newUser.getUserBio());
		} 
		
		
		if (!(newUser.getUserSpecies()=="")) {
			System.out.println("changing Species");
			currentUser.setUserSpecies(newUser.getUserSpecies());
		}
		
		userDao.save(currentUser);
		return currentUser;
	}

}

