package com.exercise.project.service.blog.post;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.project.entity.blog.Post;
import com.exercise.project.exception.ResourceNotFoundException;
import com.exercise.project.repository.blog.PostRepository;
import com.exercise.project.service.BaseService;

@Service
public class PostService extends BaseService implements PostServiceInterface {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post persistPost(Post post) {
        post.setId(UUID.randomUUID());

        return postRepository.save(post);
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getById(String id) {
        return postRepository.findById(UUID.fromString(id)).orElseThrow(
            () -> new ResourceNotFoundException("tag not found with this id : " + id));
    }

}
