package com.exercise.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.response.ApiResponse;

@RestController
public class HomeController {

    @GetMapping("/test")
    public ResponseEntity<ApiResponse> testRequest() {
        return ResponseEntity.ok(
            new ApiResponse(
                "Hello World ! from MCP Client",
                true,
                "this is a message from MCP Client"
            )
        );
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/security-test")
    public ResponseEntity<ApiResponse> testSecuredRequest() {
        return ResponseEntity.ok(
            new ApiResponse(
                "Hello World !",
                true,
                "this is a message"
            )
        );
    }

}
