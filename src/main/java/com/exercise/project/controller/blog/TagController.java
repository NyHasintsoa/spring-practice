package com.exercise.project.controller.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.exercise.project.service.blog.tag.TagServiceInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${project.api.prefix}/tags")
public class TagController {

    @Autowired
    private TagServiceInterface tagService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "All Tags",
                    true,
                    tagService.convertAllToDto(tagService.getAll())
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addNewTag(
        @RequestBody @Valid TagRequest request) {
        try {
            Tag newTag = tagService.addTag(request);
            return ResponseEntity.ok(
                new ApiResponse(
                    "Add new Tag from Request",
                    true,
                    tagService.convertToDto(newTag)
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTagById(
        @PathVariable String id,
        @RequestBody @Valid TagRequest request) {
        try {
            Tag updatedTag = tagService.updateTagFromId(id, request);
            return ResponseEntity.ok(
                new ApiResponse(
                    "Update Tag from Request",
                    true,
                    tagService.convertToDto(updatedTag)
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(
        @PathVariable String id) {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "Get Tag By Id",
                    true,
                    tagService.convertToDto(tagService.getById(id))
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

}
