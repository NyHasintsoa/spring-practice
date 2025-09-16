package com.exercise.project.security.service.reset.token.verifier;

import com.exercise.project.entity.auth.User;

public interface PasswordResetTokenVerifierInterface {

    public User validateTokenAndFetchUser(String token);

}
