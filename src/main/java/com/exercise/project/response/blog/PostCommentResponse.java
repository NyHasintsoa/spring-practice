package com.exercise.project.response.blog;

import java.util.Date;
import java.util.UUID;

import com.exercise.project.model.dto.UserDto;
import com.exercise.project.model.entity.blog.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCommentResponse {

    private UUID id;

    private String slug;

    private String title;

    private String content;

    private String headerImagePath;

    private Date publishedAt;

    private Date updatedAt;

    private UserDto author;

    private Long commentCount;

    public PostCommentResponse(Post post, Long commentCount) {
        this.id = post.getId();
        this.slug = post.getSlug();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.headerImagePath = post.getHeaderImagePath();
        this.publishedAt = post.getPublishedAt();
        this.updatedAt = post.getUpdatedAt();
        this.author = new UserDto(post.getAuthor());
        this.commentCount = commentCount;
    }

}
