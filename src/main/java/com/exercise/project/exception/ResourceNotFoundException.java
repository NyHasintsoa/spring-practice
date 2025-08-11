package com.exercise.project.exception;

import jakarta.servlet.http.HttpServletResponse;

public class ResourceNotFoundException extends ResponseException {

    public ResourceNotFoundException(String message) {
        super(message);
        this.statusCode = HttpServletResponse.SC_NOT_FOUND;
    }

    public ResourceNotFoundException() {
        super("Not Found");
        this.statusCode = HttpServletResponse.SC_NOT_FOUND;
    }

}
