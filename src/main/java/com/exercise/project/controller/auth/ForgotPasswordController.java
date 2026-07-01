package com.exercise.project.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.response.ApiResponse;
import com.exercise.project.security.request.ForgotPasswordRequest;
import com.exercise.project.security.request.ResetPasswordRequest;
import com.exercise.project.security.service.reset.password.PasswordResetService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Forgot password")
@RequestMapping("${project.api.prefix}/auth")
public class ForgotPasswordController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(
        @RequestBody ForgotPasswordRequest request
    ) {
        passwordResetService.requestPasswordReset(request);

        return ResponseEntity.ok(
            new ApiResponse("Reset password Request", true, request)
        );
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<ApiResponse> resetPassword(
        @PathVariable String token,
        @Valid @RequestBody ResetPasswordRequest request
    ) {
        passwordResetService.updatePassword(request, token);

        return ResponseEntity.ok(
            new ApiResponse("Reset password Request", true, request)
        );
    }
}
