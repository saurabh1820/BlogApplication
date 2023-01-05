package com.blogApplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogApplication.entities.Category;
import com.blogApplication.entities.Post;
import com.blogApplication.entities.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	public List<Post> findByUser(User user);
	
	public List<Post> findByCategory(Category category);
	
//	//@Query("select p from post p where p.tile like :key")
//	public	List<Post> searchByTitle(@Param("key") String title);
}
