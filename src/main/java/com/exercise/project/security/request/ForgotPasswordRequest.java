package com.exercise.project.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ForgotPasswordRequest {

    @Email
    @NotBlank(message = "NOT_BLANK_VALIDATION")
    @Email(message = "NOT_VALID_EMAIL_VALIDATION")
    private String email;

}
