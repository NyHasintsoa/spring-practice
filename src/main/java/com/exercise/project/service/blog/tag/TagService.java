package com.exercise.project.service.blog.tag;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.project.entity.blog.Tag;
import com.exercise.project.exception.ResourceNotFoundException;
import com.exercise.project.repository.blog.TagRepository;

@Service
public class TagService implements TagServiceInterface {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag persistTag(Tag tag) {
        tag.setId(UUID.randomUUID());

        return tagRepository.save(tag);
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getById(String id) {
        return tagRepository.findById(UUID.fromString(id)).orElseThrow(
            () -> new ResourceNotFoundException("tag not found with this id : " + id));
    }

}
