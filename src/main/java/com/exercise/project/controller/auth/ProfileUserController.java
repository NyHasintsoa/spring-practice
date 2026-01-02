package com.exercise.project.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.response.ApiResponse;
import com.exercise.project.security.request.ProfileUserRequest;
import com.exercise.project.security.response.UserInfoResponse;
import com.exercise.project.security.service.auth.AuthServiceInterface;

@RestController
@RequestMapping("${project.api.prefix}/auth")
public class ProfileUserController {

    @Autowired
    private AuthServiceInterface authService;

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateProfile(
        @RequestBody ProfileUserRequest request
    ) {
        UserInfoResponse userInfo = authService.updateUserProfile(request);

        return ResponseEntity.ok(
            new ApiResponse("Profile User Updated successfully", true, userInfo)
        );
    }

}
