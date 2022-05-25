package zook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;
	private String serverEmail = "zook.social.app@gmail.com";
	private String url = "http://ec2-54-241-44-71.us-west-1.compute.amazonaws.com:9039";
//	private String url = "http://localhost:9039";
	
	public void sendWelcomeEmail(String toEmail, String userFName) {
		String welcomeMsg = "Hi " + userFName + "!\nWe are glad you joined"
				+ " us at Zook Social!";
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(serverEmail);
		mail.setTo(toEmail);
		mail.setSubject("Welcome to Zook!");
		mail.setText(welcomeMsg);
		emailSender.send(mail);
		System.out.println("welcome email was sent!");
	}
	
	public void sendPasswordResetLink(String token, String toEmail) {
		String resetLink = url + "/change?token=" + token;
		String resetMsg = "A password reset was requested. Please use the link"
				+ "below to reset your password. If you did not request a password"
				+ " reset, you can disregard this message.\n"
				+ resetLink;
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(serverEmail);
		mail.setTo(toEmail);
		mail.setSubject("Password Change Requested.");
		mail.setText(resetMsg);
		emailSender.send(mail);
		System.out.println("Password reset email was sent to user");
	}
	
	public void sendEmail() {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("zook.social.app@gmail.com");
		mail.setTo("chj.castro@gmail.com");
		mail.setSubject("Welcome to the Java Email Sender!");
		mail.setText("Hello! This is a test from the Server Email Service!!!");
		emailSender.send(mail);
		System.out.println("email was sent!");
	}
	
}
