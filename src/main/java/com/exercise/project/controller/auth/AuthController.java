package com.exercise.project.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.exception.InvalidRefreshTokenException;
import com.exercise.project.response.ApiResponse;
import com.exercise.project.security.request.RefreshTokenRequest;
import com.exercise.project.security.request.RegisterRequest;
import com.exercise.project.security.request.SignInRequest;
import com.exercise.project.security.service.auth.AuthServiceInterface;
import com.exercise.project.security.service.redis.login.RedisLoginAttemptServiceInterface;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${project.api.prefix}/auth")
public class AuthController {

    @Autowired
    private AuthServiceInterface authService;

    @Autowired
    private RedisLoginAttemptServiceInterface loginAttemptService;

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
        } catch (BadCredentialsException e) {
            Long attemtp = this.loginAttemptService.loginFailed(request.getEmail());

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ApiResponse(
                    e.getMessage(),
                    false,
                    attemtp));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiResponse(
                    e.getMessage(),
                    false,
                    HttpServletResponse.SC_UNAUTHORIZED));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
        @RequestBody @Valid RegisterRequest request) {
        try {
            this.authService.register(request);

            return ResponseEntity.ok(
                new ApiResponse(
                    "Message for email verification is sended to the user, please verify your mailbox !",
                    true,
                    null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    e.getMessage(),
                    false,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/confirm-email")
    public ResponseEntity<ApiResponse> confirmEmail(
        @RequestParam String token) {
        try {
            this.authService.confirmUserByToken(token);

            return ResponseEntity.ok(
                new ApiResponse(
                    "User email verified successfully",
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
        @RequestHeader("Authorization") String authHeader,
        @PathVariable String id) {
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
