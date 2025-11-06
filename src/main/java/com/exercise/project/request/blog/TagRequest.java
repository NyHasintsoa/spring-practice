package com.exercise.project.request.blog;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagRequest {

    @NotBlank
    private String name;

}
