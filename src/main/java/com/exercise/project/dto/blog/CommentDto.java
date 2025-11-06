package com.exercise.project.dto.blog;

import java.util.Date;
import java.util.UUID;

import com.exercise.project.dto.UserDto;
import com.exercise.project.entity.blog.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {

    private UUID id;

    private String content;

    private Date publishedAt;

    private UserDto author;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.publishedAt = comment.getPublishedAt();
        this.author = new UserDto(comment.getAuthor());
    }

}
