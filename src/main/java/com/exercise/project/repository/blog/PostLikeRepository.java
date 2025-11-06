package com.exercise.project.repository.blog;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercise.project.entity.blog.PostLike;
import java.util.Optional;
import com.exercise.project.entity.auth.User;
import com.exercise.project.entity.blog.Post;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, UUID> {

    Optional<PostLike> findByPostAndAuthor(Post post, User author);

    long countByPost(Post post);

}
