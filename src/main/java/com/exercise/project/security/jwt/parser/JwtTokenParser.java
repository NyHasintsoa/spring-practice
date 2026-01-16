package com.exercise.project.security.jwt.parser;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtTokenParser {

    public String parseJwt(HttpServletRequest request);

}
