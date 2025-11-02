package com.exercise.project.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileUserRequest {

    public String firstname;

    @NotBlank
    public String lastname;

    @NotBlank
    public String phone;

}
