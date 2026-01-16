package com.exercise.project.controller.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.model.entity.blog.Tag;
import com.exercise.project.request.blog.TagRequest;
import com.exercise.project.response.ApiResponse;
import com.exercise.project.service.blog.tag.TagService;

import jakarta.validation.Valid;

@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "Blog Tag")
@RequestMapping("${project.api.prefix}/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        return ResponseEntity.ok(
            new ApiResponse(
                "All Tags",
                true,
                tagService.convertAllToDto(tagService.getAll())
            )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addNewTag(
        @RequestBody @Valid TagRequest request
    ) {
        Tag newTag = tagService.addTag(request);
        return ResponseEntity.ok(
            new ApiResponse(
                "Add new Tag from Request",
                true,
                tagService.convertToDto(newTag)
            )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTagById(
        @PathVariable String id,
        @RequestBody @Valid TagRequest request
    ) {
        Tag updatedTag = tagService.updateTagFromId(id, request);
        return ResponseEntity.ok(
            new ApiResponse(
                "Update Tag from Request",
                true,
                tagService.convertToDto(updatedTag)
            )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(
        @PathVariable String id
    ) {
        return ResponseEntity.ok(
            new ApiResponse(
                "Get Tag By Id",
                true,
                tagService.convertToDto(tagService.getById(id))
            )
        );
    }

}
