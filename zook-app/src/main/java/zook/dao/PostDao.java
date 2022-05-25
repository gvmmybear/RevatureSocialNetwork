package zook.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import zook.model.Post;
import zook.model.User;

public interface PostDao extends JpaRepository<Post, Integer>{
	
	ArrayList<Post> findAllByPostOwner(User user);
	
	Post findByPostID(int i);
}
