package com.exercise.project.exception;

public class UserAccountLockedException extends RuntimeException {

    public UserAccountLockedException() {
        super("User's Account is locked");
    }

}
