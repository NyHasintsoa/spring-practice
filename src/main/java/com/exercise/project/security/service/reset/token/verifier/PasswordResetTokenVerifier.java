package com.exercise.project.security.service.reset.token.verifier;

import com.exercise.project.model.entity.auth.User;

public interface PasswordResetTokenVerifier {

    public User validateTokenAndFetchUser(String token);

}
