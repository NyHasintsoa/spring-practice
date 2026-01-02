package com.exercise.project.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException()
    {
        super("RESOURCE_ALREADY_EXIST");
    }

    public AlreadyExistsException(String message)
    {
        super(message);
    }
}
