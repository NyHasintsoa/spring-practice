package com.exercise.project.security.jwt.parser;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtTokenParserInterface {

    public String parseJwt(HttpServletRequest request);

}
