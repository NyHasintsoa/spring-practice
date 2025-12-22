package com.exercise.project.security.service.auth;

import java.net.URI;

import com.exercise.project.model.dto.UserDto;
import com.exercise.project.model.entity.auth.User;
import com.exercise.project.security.request.ProfileUserRequest;
import com.exercise.project.security.request.RefreshTokenRequest;
import com.exercise.project.security.request.RegisterRequest;
import com.exercise.project.security.request.SignInRequest;
import com.exercise.project.security.response.JwtResponse;
import com.exercise.project.security.response.UserInfoResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthServiceInterface {

    /**
     * Sign in User with Sign in Request
     */
    public JwtResponse signIn(SignInRequest request);

    /**
     * Register User with Register Request
     */
    public void register(RegisterRequest request);

    /**
     * Confirm User By Token
     */
    public void confirmUserByToken(String token);

    /**
     * Get User Informations to show in the response
     */
    public UserInfoResponse getConnectedUserInfo();

    /**
     * Generate Refresh token from refresh token request
     */
    public JwtResponse refreshToken(RefreshTokenRequest request);

    /**
     * Log out user and revoke token
     */
    public void logout(HttpServletRequest request);

    /**
     * Build Redirect URL after payment successfully
     */
    public URI buildRedirectUrl();

    /**
     * Update User profile
     */
    public UserInfoResponse updateUserProfile(ProfileUserRequest request);

    /**
     * Convert User To DTO
     */
    public UserDto convertToDto(User data);

}
