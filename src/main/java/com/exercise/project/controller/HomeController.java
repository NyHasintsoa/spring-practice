package com.exercise.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Home")
public class HomeController {

    @Operation(
        description = "Test endpoint to show if server running"
    )
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

    @Operation(
        description = "Test secure endpoint"
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/security-test")
    public ResponseEntity<ApiResponse> testSecuredRequest() {
        return ResponseEntity.ok(
            new ApiResponse(
                "Hello World !",
                true,
                "this message appear when connected"
            )
        );
    }

}
