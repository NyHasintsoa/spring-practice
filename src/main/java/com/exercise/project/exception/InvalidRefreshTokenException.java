package com.exercise.project.exception;

import jakarta.servlet.http.HttpServletResponse;

public class InvalidRefreshTokenException extends ResponseException {

    public InvalidRefreshTokenException(String message) {
        super(message);
        this.statusCode = HttpServletResponse.SC_UNAUTHORIZED;
    }

    public InvalidRefreshTokenException() {
        super("Invalid Refresh Token");
        this.statusCode = HttpServletResponse.SC_UNAUTHORIZED;
    }

}
