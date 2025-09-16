package com.exercise.project.security.request;

import com.exercise.project.validation.constraints.PasswordMatches;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@PasswordMatches(password = "newPassword", confirmPassword = "confirmPassword")
public class ResetPasswordRequest {

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;

}
