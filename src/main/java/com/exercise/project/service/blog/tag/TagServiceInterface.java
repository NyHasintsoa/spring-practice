package com.exercise.project.service.blog.tag;

import java.util.List;
import java.util.Optional;

import com.exercise.project.dto.blog.TagDto;
import com.exercise.project.entity.blog.Tag;
import com.exercise.project.exception.ResourceNotFoundException;
import com.exercise.project.request.blog.TagRequest;

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
     * Add new Tag from Request
     */
    public Tag addTag(TagRequest request);

    /**
     * Update Existing Tag from Request
     */
    public Tag updateTagFromId(String id, TagRequest request);

    /**
     * Get Tag By Id
     * 
     * @throws ResourceNotFoundException
     */
    public Tag getById(String id);

    /**
     * Get All Tags
     */
    public List<Tag> getAll();

    /**
     * Get By Name
     */
    public Optional<Tag> findByName(String name);

    /**
     * Convert Tag to DTO
     */
    public TagDto convertToDto(Tag data);

    /**
     * Convert All To DTO
     */
    public List<Object> convertAllToDto(List<Tag> datas);

}
