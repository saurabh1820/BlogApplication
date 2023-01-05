package com.blogApplication.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApplication.entities.Comment;
import com.blogApplication.entities.Post;
import com.blogApplication.exceptions.ResourceNotFoundException;
import com.blogApplication.payloads.CommentDto;
import com.blogApplication.repositories.CommentRepository;
import com.blogApplication.repositories.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, long postId) {
		
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","PostId", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment comment2 = this.commentRepository.save(comment);
		return this.modelMapper.map(comment2, CommentDto.class);
	}

	@Override
	public void deleteComment(long commentId) {
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
		this.commentRepository.delete(comment);
	}

}
