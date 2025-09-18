package com.exercise.project.security.service.reset.password;

import com.exercise.project.security.request.ForgotPasswordRequest;
import com.exercise.project.security.request.ResetPasswordRequest;

public interface PasswordResetServiceInterface {

    public void requestPasswordReset(ForgotPasswordRequest request);

    public void updatePassword(ResetPasswordRequest request, String fullToken);

    public void cleanUpExpiredTokens();

}
