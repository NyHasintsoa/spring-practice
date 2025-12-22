package com.exercise.project.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.response.ApiResponse;
import com.exercise.project.security.request.ForgotPasswordRequest;
import com.exercise.project.security.request.ResetPasswordRequest;
import com.exercise.project.security.service.reset.password.PasswordResetServiceInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${project.api.prefix}/auth")
public class ForgotPasswordController {

    @Autowired
    private PasswordResetServiceInterface passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(
        @RequestBody ForgotPasswordRequest request) {
        passwordResetService.requestPasswordReset(request);

        return ResponseEntity.ok(
            new ApiResponse("Reset password Request", true, request));
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<ApiResponse> resetPassword(
        @PathVariable String token,
        @Valid @RequestBody ResetPasswordRequest request) {
        passwordResetService.updatePassword(request, token);

        return ResponseEntity.ok(
            new ApiResponse("Reset password Request", true, request));
    }
}
