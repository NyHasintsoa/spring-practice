package com.exercise.project.exception;

import jakarta.servlet.http.HttpServletResponse;

public abstract class ResponseException extends RuntimeException {

    protected Integer statusCode;

    public ResponseException(String message) {
        super(message);
        this.statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

}
