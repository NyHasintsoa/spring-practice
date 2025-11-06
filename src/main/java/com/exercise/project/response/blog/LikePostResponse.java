package com.exercise.project.response.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikePostResponse {

    private Boolean isLiked;

    private String articleId;

    private long likeCount;

}
