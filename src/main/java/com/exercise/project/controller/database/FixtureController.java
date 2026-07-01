package com.exercise.project.controller.database;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.fixtures.AppFixtures;
import com.exercise.project.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Fixtures")
@RequestMapping("${project.api.prefix}/databases")
public class FixtureController {

    private final AppFixtures fixtures;

    @GetMapping("/fixtures")
    public ResponseEntity<ApiResponse> makeFixtures() {
        fixtures.init();

        return ResponseEntity.ok(
            new ApiResponse(
                "Database Populated successfully",
                true,
                null
            )
        );
    }

}
