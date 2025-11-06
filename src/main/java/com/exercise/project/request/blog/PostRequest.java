package com.exercise.project.request.blog;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private MultipartFile imageFile;

    @Size(min = 1, max = 5)
    private Set<String> tags;

}
