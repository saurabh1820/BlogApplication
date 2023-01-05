package com.blogApplication.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogApplication.entities.Category;
import com.blogApplication.entities.Post;
import com.blogApplication.entities.User;
import com.blogApplication.exceptions.ResourceNotFoundException;
import com.blogApplication.payloads.CategoryDto;
import com.blogApplication.payloads.PostDto;
import com.blogApplication.payloads.PostResponse;
import com.blogApplication.repositories.CategoryRepository;
import com.blogApplication.repositories.PostRepository;
import com.blogApplication.repositories.UserRepository;
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	//create
	@Override
	public PostDto createPost(PostDto postDto,long userId,long categoryId) {
	
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "UserId", userId));
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.jpg");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post post2 = this.postRepository.save(post);
		return this.modelMapper.map(post2, PostDto.class);
	}

	//update post
	@Override
	public PostDto updatepost(PostDto postDto,long postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "PostId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post post2 = this.postRepository.save(post);
		
		return this.modelMapper.map(post2, PostDto.class);
	}

	//delete post
	@Override
	public void deletePost(long postId) {
		
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "PostId", postId));
		this.postRepository.delete(post);
	}

	//get all posts
	@Override
	public PostResponse getAllPost(Integer pageNumber ,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		//OR
//		Sort sort=null;
//		if(sortDir.equalsIgnoreCase("asc"))
//		{
//			sort=Sort.by(sortBy).ascending();
//		}
//		else
//		{
//			sort=Sort.by(sortBy).descending();
//		}
		
		Pageable p=PageRequest.of(pageNumber,pageSize, sort);
		
		Page<Post> pagePost = this.postRepository.findAll(p);
		List<Post> posts = pagePost.getContent();
		
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	//get one post
	@Override
	public PostDto getpostById(long postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","PostId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	//get all posts by category
	@Override
	public List<PostDto> getPostsByCategory(long categoryId) {
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		List<Post> posts = this.postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	//get all posts by user
	@Override
	public List<PostDto> getPostsByUser(long userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "UserId", userId));
		List<Post> posts = this.postRepository.findByUser(user);
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

//	//searching
//	@Override
//	public List<PostDto> searchPosts(String keyword) {
//		List<Post> posts = this.postRepository.searchByTitle("%"+keyword+"%");
//		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//		return postDtos;
//	}

}
