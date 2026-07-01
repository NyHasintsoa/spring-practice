package com.exercise.project.repository.blog;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.project.model.entity.blog.Comment;
import com.exercise.project.model.entity.blog.Post;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Page<Comment> findByPost(Post post, Pageable pageable);

    long countByPost(Post post);

}
