package com.exercise.project.model.dto.blog;

import java.util.UUID;

import com.exercise.project.model.entity.blog.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagDto {

    private UUID id;

    private String name;

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }

}
