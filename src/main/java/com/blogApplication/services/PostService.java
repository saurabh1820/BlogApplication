package com.blogApplication.services;

import java.util.List;

import com.blogApplication.payloads.PostDto;
import com.blogApplication.payloads.PostResponse;

public interface PostService {

	//create 
	public PostDto createPost(PostDto postDto,long userId,long categoryId);
	
	//update
	public PostDto updatepost(PostDto postDto,long postId);
	
	//delete
	public void deletePost(long postId);
	
	//get all posts
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//get single post
	PostDto getpostById(long postId);
	
	//get all posts by category
	public List<PostDto> getPostsByCategory(long categoryId);
	
	//get all posts by user
	public List<PostDto> getPostsByUser(long userId);
//	
//	//search posts
//	List<PostDto> searchPosts(String keyword);
}
