package com.exercise.project.security.service.email.verifier;

import com.exercise.project.model.entity.auth.User;

public interface EmailVerifierService {

    public void sendEmailConfirmation(User user);

    public void handleEmailConfirmation(String token);

}
