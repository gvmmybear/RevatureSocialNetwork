package zook.services;

import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service	
public class TokenService {

//	private static final 
	public String generateResetToken(String email) {
		Date time = new Date();
		Random r = new Random();
		String currentTime = Long.toString(time.getTime());
		char[] tokenChars = new char[currentTime.length() * 2];
		
		// set array pointers
		int e = email.length() - 1;
		int t = 0;
		int i = 0;
		while(i < tokenChars.length){
			if(i % 2 == 0 && t < currentTime.length()) {
				tokenChars[i] = currentTime.charAt(t);
				t++;
			}
			else {	
				char random = (char)(r.nextInt(26) + 'a');
				tokenChars[i] = random;					
				}
				
			i++;				
		}
		// creates randomized token and returns String value
		String token = String.valueOf(tokenChars);
		return token;
	}

}
