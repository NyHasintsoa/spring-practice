package com.exercise.project.security.service.reset.token.verifier;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.exercise.project.model.entity.auth.ResetPasswordRequest;
import com.exercise.project.model.entity.auth.User;
import com.exercise.project.exception.InvalidResetPasswordTokenException;
import com.exercise.project.repository.auth.ResetPasswordRequestRepository;
import com.exercise.project.security.service.reset.token.generator.PasswordResetTokenGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenVerifierImpl implements PasswordResetTokenVerifier {

    @Value("${project.secret.key}")
    private String PROJECT_SECRET_KEY;

    @Value("${project.reset.password.selector.length}")
    private Integer SELECTOR_LENGTH;

    private final PasswordResetTokenGenerator passwordResetTokenGenerator;
    private final ResetPasswordRequestRepository resetPasswordRequestRepository;

    @Override
    public User validateTokenAndFetchUser(String token) {
        String selector = token.substring(0, SELECTOR_LENGTH);
        Optional<ResetPasswordRequest> resetRequestOptional = resetPasswordRequestRepository.findBySelector(selector);

        if (resetRequestOptional.isEmpty()) {
            throw new InvalidResetPasswordTokenException();
        }

        ResetPasswordRequest resetRequest = resetRequestOptional.get();

        if (!verifyToken(selector, resetRequest.getHashedToken())) {
            throw new InvalidResetPasswordTokenException();
        }
        resetPasswordRequestRepository.delete(resetRequest);

        return resetRequest.getUser();
    }

    private Boolean verifyToken(String selector, String stockedToken) {
        String hashedToken = passwordResetTokenGenerator.getHashedToken(selector);

        return stockedToken.equals(hashedToken);
    }

}
