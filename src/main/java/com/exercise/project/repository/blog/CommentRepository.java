package com.exercise.project.repository.blog;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.project.entity.blog.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

}
