package com.exercise.project.controller.database;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.response.ApiResponse;

@RestController
@RequestMapping("${project.api.prefix}/database")
public class DatabaseController {

    @GetMapping("/fixtures")
    public ResponseEntity<ApiResponse> makeFixtures() {
        try {
            return ResponseEntity.ok(
                new ApiResponse("", true, ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse(
                    "INTERNAL_SERVER_ERROR",
                    false,
                    e.getMessage()));
        }
    }

}
