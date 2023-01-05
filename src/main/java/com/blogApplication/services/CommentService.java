package com.blogApplication.services;

import com.blogApplication.payloads.CommentDto;

public interface CommentService {

	public CommentDto createComment(CommentDto comment,long postId);
	
	public void deleteComment(long commentId);
}
