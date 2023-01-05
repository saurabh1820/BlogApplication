package com.blogApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApplication.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
