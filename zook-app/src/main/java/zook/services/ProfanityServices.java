package zook.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zook.dao.ProfanityDao;
import zook.model.Profanity;

@Service
public class ProfanityServices {
	
	@Autowired
	private ProfanityDao profanityDao;
	
	public List<Profanity> allProfanity(){
		List<Profanity> badWordsList = new ArrayList<>();
		
		badWordsList = profanityDao.findAll();
		
		return badWordsList;
	}
	
	public List<String> badWords(){
		List<String> cantSayThat = new ArrayList<>();
		List<Profanity> badWordsList = new ArrayList<>();
		badWordsList = profanityDao.findAll();
		for (int i=0;i<badWordsList.size();i++) {
			cantSayThat.set(i, badWordsList.get(i).getProfanity());
		}
		
		return cantSayThat;
	}

}
