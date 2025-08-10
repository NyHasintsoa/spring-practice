package com.exercise.project.exception;

import jakarta.servlet.http.HttpServletResponse;

public class UserNotFoundException extends ResponseException {

    public UserNotFoundException() {
        super("User not Found !");
        this.statusCode = HttpServletResponse.SC_NOT_FOUND;
    }

    public UserNotFoundException(String message) {
        super(message);
        this.statusCode = HttpServletResponse.SC_NOT_FOUND;
    }

    public UserNotFoundException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
