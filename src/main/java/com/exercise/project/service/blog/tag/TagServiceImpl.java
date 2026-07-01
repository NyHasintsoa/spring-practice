package com.exercise.project.service.blog.tag;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.exercise.project.model.dto.blog.TagDto;
import com.exercise.project.model.entity.blog.Tag;
import com.exercise.project.exception.ResourceNotFoundException;
import com.exercise.project.repository.blog.TagRepository;
import com.exercise.project.request.blog.TagRequest;
import com.exercise.project.service.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl extends BaseService<Tag> implements TagService {

    private final TagRepository tagRepository;

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
    public Tag addTag(TagRequest request) {
        Tag tag = new Tag();
        tag.setName(request.getName());

        return persistTag(tag);
    }

    @Override
    public Tag updateTagFromId(String id, TagRequest request) {
        Tag tag = getById(id);
        tag.setName(request.getName());

        return saveTag(tag);
    }

    @Override
    public Tag getById(String id) {
        return tagRepository.findById(UUID.fromString(id)).orElseThrow(
            () -> new ResourceNotFoundException("tag not found with this id : " + id));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public TagDto convertToDto(Tag data) {
        return new TagDto(data);
    }

}
