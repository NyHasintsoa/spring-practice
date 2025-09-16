package com.exercise.project.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInRequest {

    @NotBlank(message = "NOT_BLANK_VALIDATION")
    @Email(message = "NOT_VALID_EMAIL_VALIDATION")
    private String email;

    @NotBlank
    @Size(min = 5, message = "PASSWORD_NOT_IN_SIZE_VALIDATION")
    private String password;

    private Boolean rememberMe;

}
