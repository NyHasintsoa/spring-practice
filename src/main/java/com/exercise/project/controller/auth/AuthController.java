package com.exercise.project.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.exception.AlreadyExistsException;
import com.exercise.project.exception.InvalidRefreshTokenException;
import com.exercise.project.response.ApiResponse;
import com.exercise.project.security.request.RefreshTokenRequest;
import com.exercise.project.security.request.RegisterRequest;
import com.exercise.project.security.request.SignInRequest;
import com.exercise.project.security.response.JwtResponse;
import com.exercise.project.security.service.auth.AuthServiceInterface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${project.api.prefix}/auth")
public class AuthController {

    @Autowired
    private AuthServiceInterface authService;

    @Value("${project.jwt.cookie.token.storage.key}")
    private String JWT_COOKIE_STORAGE_KEY;

    @Value("${project.jwt.token.expiration}")
    private Integer JWT_TOKEN_EXPIRATION;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getCurrentUser() {
        return ResponseEntity.ok(
            new ApiResponse(
                "current user informations",
                true,
                authService.getConnectedUserInfo()
            )
        );
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse> signIn(
        @RequestBody @Valid SignInRequest request,
        HttpServletResponse response
    ) {
        try {
            JwtResponse jwtResponse = authService.signIn(request);
            ResponseCookie jwtCookie = ResponseCookie.from(
                JWT_COOKIE_STORAGE_KEY,
                jwtResponse.getToken()).httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(JWT_TOKEN_EXPIRATION)
                .build();
            response.addHeader("Set-Cookie", jwtCookie.toString());

            return ResponseEntity.ok(
                new ApiResponse(
                    "Sign in Request",
                    true,
                    jwtResponse
                )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ApiResponse(
                    e.getMessage(),
                    false,
                    "Attemps number here"
                )
            );
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse> register(
        @RequestBody @Valid RegisterRequest request
    ) {
        try {
            authService.register(request);

            return ResponseEntity.ok(
                new ApiResponse(
                    "Message for email verification is sended to the user, please verify your mailbox !",
                    true,
                    null
                )
            );
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse(
                    e.getMessage(),
                    false,
                    "EMAIL_NOT_UNIQUE_VALIDATION"
                )
            );
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refreshToken(
        @RequestBody @Valid RefreshTokenRequest request
    ) {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "Refresh token generated successfully",
                    true,
                    authService.refreshToken(request)
                )
            );
        } catch (InvalidRefreshTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiResponse(
                    "UNAUTHORIZED",
                    false,
                    e.getMessage()
                )
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(
        HttpServletResponse response,
        HttpServletRequest request
    ) {
        authService.logout(request);

        ResponseCookie jwtCookie = ResponseCookie.from(
            JWT_COOKIE_STORAGE_KEY, "")
            .secure(true)
            .path("/")
            .maxAge(0)
            .build();
        response.addHeader("Set-Cookie", jwtCookie.toString());

        return ResponseEntity.ok(
            new ApiResponse(
                "user logged out successfully !",
                true,
                null
            )
        );
    }

}
