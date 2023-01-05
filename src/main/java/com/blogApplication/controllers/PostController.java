package com.blogApplication.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogApplication.config.AppConstants;
import com.blogApplication.payloads.ApiResponse;
import com.blogApplication.payloads.PostDto;
import com.blogApplication.payloads.PostResponse;
import com.blogApplication.services.FileService;
import com.blogApplication.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable long userId,@PathVariable long categoryId){
		
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	//get postByUser
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable long userId){
	
		List<PostDto> posts = this.postService.getPostsByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//get postByCatgory
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable long categoryId){
		
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//get All_Posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false) String sortDir){
		
	 PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
		
	
	}
	
	//getting postById
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable long postId){
		PostDto post = this.postService.getpostById(postId);
		
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	//delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable long postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post is Successfully Deleted !!",true);
	}
	
	//update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable long postId){
		PostDto updatepost = this.postService.updatepost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatepost,HttpStatus.OK);
	}
	
//	//searching
//	@GetMapping("/posts/search/{keywords}")
//	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
//		
//		List<PostDto> result = this.postService.searchPosts(keywords);
//		
//		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
//	}
	
	//post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable long postId) throws Exception{
		
		PostDto postDto = this.postService.getpostById(postId);
		String fileName  = this.fileService.uploadImage(path, image);
	
		postDto.setImageName(fileName);
		PostDto updatepost = this.postService.updatepost(postDto, postId);
		return new ResponseEntity<PostDto>(updatepost,HttpStatus.OK); 
	}
	
	//method to serve file
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
