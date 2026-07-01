package com.exercise.project.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.response.ApiResponse;
import com.exercise.project.security.request.ProfileUserRequest;
import com.exercise.project.security.response.UserInfoResponse;
import com.exercise.project.security.service.auth.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Profile")
@RequestMapping("${project.api.prefix}/auth")
public class ProfileUserController {

    private final AuthService authService;

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
