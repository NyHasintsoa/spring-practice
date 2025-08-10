package com.exercise.project.security.service.auth;

import com.exercise.project.security.request.RegisterRequest;
import com.exercise.project.security.request.SignInRequest;
import com.exercise.project.security.response.JwtResponse;
import com.exercise.project.security.response.UserInfoResponse;

public interface AuthServiceInterface {

    /**
     * Sign in User with Sign in Request
     */
    public JwtResponse signIn(SignInRequest request);

    /**
     * Register User with Register Request
     */
    public JwtResponse register(RegisterRequest request);

    /**
     * Get User Informations to show in the response
     */
    public UserInfoResponse getConnectedUserInfo();

}
