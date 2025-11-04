package com.exercise.project.service.blog.post;

import com.exercise.project.entity.blog.Post;
import com.exercise.project.exception.ResourceNotFoundException;

public interface PostServiceInterface {

    /**
     * Store Post in the database
     */
    public Post persistPost(Post post);

    /**
     * Persist Post in the database
     */
    public Post savePost(Post post);

    /**
     * Get Post By Id
     * 
     * @throws ResourceNotFoundException
     */
    public Post getById(String id);

}
