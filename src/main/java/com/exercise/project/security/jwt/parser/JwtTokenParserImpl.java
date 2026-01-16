package com.exercise.project.security.jwt.parser;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenParserImpl implements JwtTokenParser {

    @Value("${project.jwt.bearer.prefix}")
    private String BEARER_PREFIX;

    @Value("${project.jwt.cookie.token.storage.key}")
    private String COOKIE_TOKEN_STORAGE;

    @Override
    public String parseJwt(HttpServletRequest request) {
        String jwt = getJwtFromHeader(request);
        if (jwt == null) {
            jwt = getJwtFromCookie(request);
        }
        return jwt;
    }

    private String getJwtFromHeader(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER_PREFIX)) {
            return headerAuth.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;

        return Arrays.stream(cookies)
            .filter(cookie -> COOKIE_TOKEN_STORAGE.equals(cookie.getName()))
            .findFirst()
            .map(Cookie::getValue)
            .orElse(null);
    }

}
