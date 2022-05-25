package zook.controllers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import zook.model.Profanity;
import zook.services.ProfanityServices;

@RestController
public class ProfanityController {
	
	@Autowired
	ProfanityServices myProfanityServices = new ProfanityServices();
	
	@CrossOrigin
	@GetMapping("/allprofanity")
	public List<Profanity> allProfanity(){
		List<Profanity> badWords = new ArrayList<>();
		badWords = myProfanityServices.allProfanity();
		
		return badWords;
	}
	

}
