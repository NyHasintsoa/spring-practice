package com.exercise.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.response.ApiResponse;

@RestController
public class HomeController {

    @GetMapping("/test")
    public ResponseEntity<ApiResponse> testRequest() {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "Hello World ! from MCP Client",
                    true,
                    "this is a message from MCP Client"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    e.getMessage(),
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/security-test")
    public ResponseEntity<ApiResponse> testSecuredRequest() {
        try {
            return ResponseEntity.ok(
                new ApiResponse(
                    "Hello World !",
                    true,
                    "this is a message"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    e.getMessage(),
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

}
