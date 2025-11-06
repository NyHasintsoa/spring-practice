package com.exercise.project.repository.blog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercise.project.entity.blog.Post;
import com.exercise.project.entity.auth.User;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findByAuthor(User author);

    Optional<Post> findBySlug(String slug);

}
