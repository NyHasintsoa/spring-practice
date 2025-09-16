package com.exercise.project.exception;

public class InvalidResetPasswordTokenException extends RuntimeException {

    public InvalidResetPasswordTokenException(String message) {
        super(message);
    }

    public InvalidResetPasswordTokenException() {
        super("invalid reset password token");
    }

}
