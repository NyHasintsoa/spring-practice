package com.exercise.project.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
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
