package com.exercise.project.security.service.reset.token.generator;

public interface PasswordResetTokenGeneratorInterface {

    public String generateRandomString(Integer length);

    public String getHashedToken(String data);

}
