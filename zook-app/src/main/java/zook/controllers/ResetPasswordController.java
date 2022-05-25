package zook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import zook.dao.AuthenticationDao;
import zook.dao.UserDao;
import zook.model.Authentication;
import zook.model.PasswordResetRequestModel;
import zook.model.User;
import zook.services.EmailService;
import zook.services.TokenService;
import zook.services.UserServices;

@RestController
public class ResetPasswordController{
	
	@Autowired
	AuthenticationDao authDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	UserServices userServices;
	
	@Autowired
	EmailService emailService;
	
	@PostMapping({"/request", "/recover"})
	public @ResponseBody User requestPasswordResetLink(@RequestBody User user) {
		User findUser = userDao.findByUserEmail(user.getUserEmail());
		try {
			String email = findUser.getUserEmail();
			String token = tokenService.generateResetToken(email);
			Authentication auth = new Authentication(token, email);
			authDao.save(auth);
			emailService.sendPasswordResetLink(token, email);
			System.out.println("Password reset link was sent!");
		}catch(NullPointerException nullException) {
			System.out.println("Invalid email reset requested.");
		}
		return user;
	}
	
	@PostMapping("/change")
	@ResponseBody
	public PasswordResetRequestModel submitPasswordReset(@RequestBody PasswordResetRequestModel pwRestModel) {
		try {
			System.out.println(pwRestModel);
			Authentication authToken = authDao.findByToken(pwRestModel.getToken());
			User user = userDao.findByUserEmail(authToken.getUserEmail());
			user.setUserPassword(userServices.rehashPassword(pwRestModel.getPassword()));
			userDao.save(user);
			authDao.deleteById(authToken.getId());
			System.out.println("a user has updated their password");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("a user has requested a stale token!");
		}
		return pwRestModel;
	}

}
