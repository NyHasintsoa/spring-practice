package com.exercise.project.security.service.reset.token.generator;

public interface PasswordResetTokenGenerator {

    public String generateRandomString(Integer length);

    public String getHashedToken(String data);

}
