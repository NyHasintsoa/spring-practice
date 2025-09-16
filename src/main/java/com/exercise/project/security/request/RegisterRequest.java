package com.exercise.project.security.request;

import com.exercise.project.validation.constraints.PasswordMatches;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@PasswordMatches(password = "password", confirmPassword = "confirmPassword")
public class RegisterRequest {

    @NotBlank(message = "NOT_BLANK_VALIDATION")
    @Email(message = "NOT_VALID_EMAIL_VALIDATION")
    private String email;

    private String fullname;

    @NotBlank
    @Size(min = 5, message = "NOT_IN_SIZE_VALIDATION")
    private String password;

    @NotBlank
    private String confirmPassword;

}
