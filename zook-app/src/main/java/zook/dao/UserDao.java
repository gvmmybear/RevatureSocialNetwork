package zook.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zook.model.User;

public interface UserDao extends JpaRepository<User, Integer>{
	User findByUserId(int userId);
	User findByUserEmail(String userEmail);
	List<User> findAll();

	
	User findByUserEmailAndUserPassword(String userEmail, String userPassword);
	

}
