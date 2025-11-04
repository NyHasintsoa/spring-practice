package com.exercise.project.service.blog.tag;

import com.exercise.project.entity.blog.Tag;
import com.exercise.project.exception.ResourceNotFoundException;

public interface TagServiceInterface {

    /**
     * Store Tag in the database
     */
    public Tag persistTag(Tag tag);

    /**
     * Persist Tag in the database
     */
    public Tag saveTag(Tag tag);

    /**
     * Get Tag By Id
     * 
     * @throws ResourceNotFoundException
     */
    public Tag getById(String id);

}
