package com.exercise.project.service.blog.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.exercise.project.model.dto.blog.PostDto;
import com.exercise.project.model.entity.blog.Comment;
import com.exercise.project.model.entity.blog.Post;
import com.exercise.project.exception.ResourceNotFoundException;
import com.exercise.project.request.blog.CommentRequest;
import com.exercise.project.request.blog.PostRequest;
import com.exercise.project.response.blog.LikePostResponse;
import com.exercise.project.response.blog.PostCommentResponse;

import jakarta.servlet.http.HttpServletRequest;

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
     * Submit new Post
     */
    public Post submitNewPost(PostRequest request);

    /**
     * Submit comment for Post by postId
     */
    public Comment submitCommentByPostId(String postId, CommentRequest request);

    /**
     * Like Post By Post Id
     */
    public LikePostResponse likePostByPostId(String postId, HttpServletRequest request);

    /**
     * Get Post By Id
     * 
     * @throws ResourceNotFoundException
     */
    public Post getById(String id);

    /**
     * Get Post By Slug
     */
    public Post getBySlug(String slug);

    /**
     * Get All Posts
     */
    public List<Post> getAll();

    /**
     * Get All Posts for the connected User
     */
    public List<Post> getOwnPosts();

    /**
     * Get Paginated Comments from Post
     */
    public Page<Comment> getPaginatedCommentFromPost(Post post, Pageable pageable);

    /**
     * Convert Post To DTO
     */
    public PostDto convertToDto(Post data);

    /**
     * Convert Post to DTO and get Comments size
     */
    public PostCommentResponse convertToResponse(Post post);

    /**
     * Convert All Posts to DTO
     */
    public List<Object> convertAllToDto(List<Post> datas);

    /**
     * Convert All Posts to DTO and Count Comments
     */
    public List<PostCommentResponse> convertAllToResponse(List<Post> posts);

}
