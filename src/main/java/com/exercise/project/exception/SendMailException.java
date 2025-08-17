package com.exercise.project.exception;

public class SendMailException extends RuntimeException {

    public SendMailException() {
        super("Mail cannot be send !");
    }

}
