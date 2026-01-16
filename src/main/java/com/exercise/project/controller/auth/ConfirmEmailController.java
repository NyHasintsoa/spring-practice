package com.exercise.project.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.project.security.service.auth.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Email Confirmation")
@RequestMapping("${project.api.prefix}/auth")
public class ConfirmEmailController {

    @Autowired
    private AuthService authService;

    @GetMapping("/confirm-email")
    public ResponseEntity<Void> confirmEmail(
        @RequestParam String token) {
        try {
            authService.confirmUserByToken(token);
        } catch (Exception e) {
        }

        return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(authService.buildRedirectUrl()).build();
    }
}
