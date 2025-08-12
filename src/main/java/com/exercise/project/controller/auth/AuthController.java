package com.exercise.project.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.exception.InvalidRefreshTokenException;
import com.exercise.project.response.ApiResponse;
import com.exercise.project.security.request.RefreshTokenRequest;
import com.exercise.project.security.request.RegisterRequest;
import com.exercise.project.security.request.SignInRequest;
import com.exercise.project.security.service.auth.AuthServiceInterface;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${project.api.prefix}/auth")
public class AuthController {

    @Autowired
    private AuthServiceInterface authService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getCurrentUser() {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "current user informations",
                    true,
                    this.authService.getConnectedUserInfo()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    e.getMessage(),
                    false,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse> signIn(
        @RequestBody @Valid SignInRequest request) {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "Sign in Request",
                    true,
                    this.authService.signIn(request)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    e.getMessage(),
                    false,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
        @RequestBody @Valid RegisterRequest request) {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "authentication request",
                    true,
                    this.authService.register(request)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    e.getMessage(),
                    false,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refreshToken(
        @RequestBody @Valid RefreshTokenRequest request) {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "Refresh token generated successfully",
                    true,
                    this.authService.refreshToken(request)));
        } catch (InvalidRefreshTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiResponse(
                    "UNAUTHORIZED",
                    false,
                    e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(
        @RequestHeader("Authorization") String authHeader) {
        try {
            this.authService.logout(authHeader);

            return ResponseEntity.ok(
                new ApiResponse(
                    "user logged out successfully !",
                    true,
                    null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

}
