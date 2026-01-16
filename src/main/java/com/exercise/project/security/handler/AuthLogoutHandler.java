package com.exercise.project.security.handler;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.exercise.project.security.jwt.parser.JwtTokenParser;
import com.exercise.project.security.service.token.JwtTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthLogoutHandler implements LogoutHandler {

    @Autowired
    private JwtTokenParser tokenParser;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Value("${project.jwt.cookie.token.storage.key}")
    private String JWT_COOKIE_STORAGE_KEY;

    @Override
    public void logout(
        HttpServletRequest request,
        HttpServletResponse response,
        @Nullable Authentication authentication
    ) {
        String token = tokenParser.parseJwt(request);
        jwtTokenService.revokeToken(token);

        ResponseCookie jwtCookie = ResponseCookie.from(
            JWT_COOKIE_STORAGE_KEY, "")
            .secure(true)
            .path("/")
            .maxAge(0)
            .build();
        response.addHeader("Set-Cookie", jwtCookie.toString());
    }

}
