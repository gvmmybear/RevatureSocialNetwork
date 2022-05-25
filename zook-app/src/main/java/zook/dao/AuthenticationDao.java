package zook.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import zook.model.Authentication;

public interface AuthenticationDao extends JpaRepository<Authentication, Integer>{

	Authentication findByToken(String token);
	void deleteByToken(String token);
//	AuthToken removeById(String tokenId);
//	void deleteAuthTokenById(String tokenId);
}
