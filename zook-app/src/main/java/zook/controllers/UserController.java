package zook.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import zook.model.User;
import zook.services.UserServices;

@RestController
public class UserController {

	@Autowired
	UserServices myUserServices = new UserServices();

	@CrossOrigin
	@PostMapping("/addUser")
	public String addUser(@RequestBody User user) {

		String added = myUserServices.addUser(user);
		System.out.println("added = " + added);
		return added;
	}

	@PostMapping("/login")
	public User loginVerification(HttpSession ses, @RequestBody User user) {

		User currentUser = myUserServices.loginVerification(user);
		if (currentUser == null) {
			System.out.println("currentUser is null");
		} else {
			System.out.println("session started");
			ses.setAttribute("CurrentUser", currentUser);
			ses.setAttribute("userId", currentUser.getUserId());
			ses.setAttribute("userFName", currentUser.getUserFName());
			ses.setAttribute("userLName", currentUser.getUserLName());
			ses.setAttribute("userEmail", currentUser.getUserEmail());
			ses.setAttribute("userSpecies", currentUser.getUserSpecies());
			ses.setAttribute("userBio", currentUser.getUserBio());
			ses.setAttribute("userProfilePic", currentUser.getUserProfilePic());
			// ses.setAttribute("posts", currentUser.getPosts());
//            System.out.println(currentUser);
			ViewController.goToProfilePage();
		}
		User ghostUser = new User();
		return ghostUser;
	}

	@PostMapping("/logMeout")
	public String logout(HttpServletRequest req) {

		/// gets the active session
		HttpSession session = req.getSession();

		session.invalidate();

		System.out.println("session invalidated");
		
		String path = "/index.html";
		req.getRequestDispatcher(path);
		return "/media/index.html";
	}

	@PostMapping("/getSes") // testing purposes & ask about the user toString, do we need to pass
							// (userPassword, userEmail)that information around
	public void getSession(HttpServletRequest req) {
		HttpSession session = req.getSession();
		System.out.println(session);

	}

	@PostMapping("/currentuser")
    public User getCurrentUserFromTheirSession(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
//		User currentUser = (User)req.getSession().getAttribute("CurrentUser");	
    	int userID = (int) req.getSession().getAttribute("userId");
    	String userFirst = (String) req.getSession().getAttribute("userFName");
    	String userLast = (String) req.getSession().getAttribute("userLName");
    	String userEmail = (String) req.getSession().getAttribute("userEmail");
    	String userProfilePic = (String) req.getSession().getAttribute("userProfilePic");
    	String userBio = (String) req.getSession().getAttribute("userBio");
    	String userSpecies = (String) req.getSession().getAttribute("userSpecies");
    	
    	
    	
    	
    	
    	User currentUser = new User(userID,userFirst,userLast,userEmail,userProfilePic,userBio,userSpecies);
//		System.out.println(currentUser);
			
//		PrintWriter printer = res.getWriter();
//		printer.write(new ObjectMapper().writeValueAsString(currentUser));
		
//    	return currentUser;
    	System.out.println(currentUser);
    	return currentUser;
	
	}

	@GetMapping("/allusers")
	public List<User> listOfUsers(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		List<User> allUsers = new ArrayList<>(myUserServices.allUsers());
		
		return allUsers;
	}
	
	
	@PostMapping("/updateProfile")
    public boolean updateProfile(HttpSession ses, @RequestBody User user) {
    	boolean update = false;
    	
    	User updatedCurrentUser = null;
    	
    	updatedCurrentUser = myUserServices.updateProfile(user);
    	
    	if(updatedCurrentUser!= null) {
    		update=true;
    		ses.setAttribute("CurrentUser", updatedCurrentUser);
            ses.setAttribute("userId", updatedCurrentUser.getUserId());
            ses.setAttribute("userFName", updatedCurrentUser.getUserFName());
            ses.setAttribute("userLName", updatedCurrentUser.getUserLName());
            ses.setAttribute("userEmail", updatedCurrentUser.getUserEmail());
            ses.setAttribute("userSpecies", updatedCurrentUser.getUserSpecies());
            ses.setAttribute("userBio", updatedCurrentUser.getUserBio());
            ses.setAttribute("userProfilePic", updatedCurrentUser.getUserProfilePic());
    	}
    	
    	System.out.println(updatedCurrentUser);
    	
    	return update;
    }
}
