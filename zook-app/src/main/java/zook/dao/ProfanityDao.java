package zook.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zook.model.Profanity;


public interface ProfanityDao extends JpaRepository<Profanity, String> {
	
	List<Profanity> findAll();
	

}
