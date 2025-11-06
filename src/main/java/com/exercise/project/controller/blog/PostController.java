package com.exercise.project.controller.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.dto.blog.CommentDto;
import com.exercise.project.entity.blog.Comment;
import com.exercise.project.entity.blog.Post;
import com.exercise.project.exception.ResourceNotFoundException;
import com.exercise.project.request.blog.CommentRequest;
import com.exercise.project.request.blog.PostRequest;
import com.exercise.project.response.ApiResponse;
import com.exercise.project.service.blog.post.PostServiceInterface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${project.api.prefix}/posts")
public class PostController {

    @Autowired
    private PostServiceInterface postService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<ApiResponse> submitNewPost(
        @ModelAttribute @Valid PostRequest request) {
        try {
            Post newPost = postService.submitNewPost(request);

            return ResponseEntity.ok(
                new ApiResponse(
                    "Submit new Post from connected User",
                    true,
                    postService.convertToResponse(newPost)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAll() {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "All Posts",
                    true,
                    postService.convertAllToDto(postService.getAll())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/author")
    public ResponseEntity<ApiResponse> getByConnectedAuthor() {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "Get Post By The connected Author",
                    true,
                    postService.convertAllToDto(postService.getOwnPosts())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<ApiResponse> getAllPostsWithCommentCount() {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "All Posts with Comment Count",
                    true,
                    postService.convertAllToResponse(postService.getAll())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/author/comment")
    public ResponseEntity<ApiResponse> getPostsWithCommentByConnectedAuthor() {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "Get All Posts and Comment Count By The connected Author",
                    true,
                    postService.convertAllToResponse(postService.getOwnPosts())));
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
                    "Get Post By Id",
                    true,
                    postService.convertToDto(postService.getById(id))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<ApiResponse> getCommentsForPost(
        @PathVariable String id,
        @PageableDefault(page = 0, size = 10, sort = "publishedAt", direction = Direction.ASC) Pageable pageable) {
        try {
            Post post = postService.getById(id);
            return ResponseEntity.ok(
                new ApiResponse(
                    "Get Paginated Comments From Post Id",
                    true,
                    postService.getPaginatedCommentFromPost(post, pageable).stream()
                        .map(comment -> new CommentDto(comment))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<ApiResponse> commentPostByPostId(
        @PathVariable String id,
        @RequestBody @Valid CommentRequest request) {
        try {
            Comment comment = postService.submitCommentByPostId(id, request);

            return ResponseEntity.ok(
                new ApiResponse(
                    "Comment Post",
                    true,
                    new CommentDto(comment)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse(
                    "NOT_FOUND",
                    false,
                    e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{id}/like")
    public ResponseEntity<ApiResponse> likePostByPostId(
        @PathVariable String id,
        HttpServletRequest request) {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "Like Post By PostId",
                    true,
                    postService.likePostByPostId(id, request)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse(
                    "NOT_FOUND",
                    false,
                    e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }
}
