package com.exercise.project.security.service.reset.password;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.exercise.project.model.entity.auth.ResetPasswordRequest;
import com.exercise.project.model.entity.auth.User;
import com.exercise.project.repository.auth.ResetPasswordRequestRepository;
import com.exercise.project.repository.auth.UserRepository;
import com.exercise.project.security.request.ForgotPasswordRequest;
import com.exercise.project.security.service.reset.token.generator.PasswordResetTokenGenerator;
import com.exercise.project.security.service.reset.token.verifier.PasswordResetTokenVerifier;

import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
class PasswordResetServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private SpringTemplateEngine templateEngine;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResetPasswordRequestRepository resetPasswordRequestRepository;

    @Mock
    private PasswordResetTokenGenerator tokenGenerator;

    @Mock
    private PasswordResetTokenVerifier tokenVerifier;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordResetServiceImpl passwordResetService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(passwordResetService, "RESET_PASSWORD_TOKEN_EXPIRATION", 3600);
        ReflectionTestUtils.setField(passwordResetService, "RESET_PASSWORD_COOLDOWN", 300);
        ReflectionTestUtils.setField(passwordResetService, "MAIL_FROM", "noreply@domain.com");
        ReflectionTestUtils.setField(passwordResetService, "NAME_FROM", "Spring Admin Society");
        ReflectionTestUtils.setField(passwordResetService, "FRONTEND_URL", "http://localhost:3000");
        ReflectionTestUtils.setField(passwordResetService, "SELECTOR_LENGTH", 20);
    }

    @Test
    void requestPasswordResetShouldPersistTokenAndSendEmailForExistingUser() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("user@example.com");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(resetPasswordRequestRepository.findByUserAndRequestedAtAfter(eq(user), any(Date.class)))
                .thenReturn(List.of());
        when(tokenGenerator.generateRandomString(20)).thenReturn("selector");
        when(tokenGenerator.getHashedToken("selector")).thenReturn("hashed-token");
        MimeMessage mimeMessage = new MimeMessage((jakarta.mail.Session) null);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(any(String.class), any(org.thymeleaf.context.IContext.class)))
                .thenReturn("<html>Reset</html>");

        passwordResetService.requestPasswordReset(new ForgotPasswordRequest("user@example.com"));

        verify(resetPasswordRequestRepository).save(any(ResetPasswordRequest.class));
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    void requestPasswordResetShouldIgnoreUnknownEmails() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        passwordResetService.requestPasswordReset(new ForgotPasswordRequest("missing@example.com"));

        verify(resetPasswordRequestRepository, never()).save(any());
    }

    @Test
    void updatePasswordShouldHashAndPersistNewPassword() {
        User user = new User();
        user.setEmail("user@example.com");
        when(tokenVerifier.validateTokenAndFetchUser("token")).thenReturn(user);
        when(passwordEncoder.encode("newPassword123")).thenReturn("hashed-password");

        passwordResetService.updatePassword(
                new com.exercise.project.security.request.ResetPasswordRequest("newPassword123", "newPassword123"),
                "token");

        verify(userRepository).save(user);
        verify(passwordEncoder).encode("newPassword123");
    }
}
