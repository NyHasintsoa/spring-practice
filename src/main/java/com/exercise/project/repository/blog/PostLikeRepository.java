package com.exercise.project.repository.blog;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.project.model.entity.blog.PostLike;
import java.util.Optional;
import com.exercise.project.model.entity.auth.User;
import com.exercise.project.model.entity.blog.Post;

public interface PostLikeRepository extends JpaRepository<PostLike, UUID> {

    Optional<PostLike> findByPostAndAuthor(Post post, User author);

    long countByPost(Post post);

}
