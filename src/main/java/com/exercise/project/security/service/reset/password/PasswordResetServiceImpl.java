package com.exercise.project.security.service.reset.password;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.exercise.project.model.entity.auth.ResetPasswordRequest;
import com.exercise.project.model.entity.auth.User;
import com.exercise.project.exception.SendMailException;
import com.exercise.project.repository.auth.ResetPasswordRequestRepository;
import com.exercise.project.repository.auth.UserRepository;
import com.exercise.project.security.request.ForgotPasswordRequest;
import com.exercise.project.security.service.reset.token.generator.PasswordResetTokenGenerator;
import com.exercise.project.security.service.reset.token.verifier.PasswordResetTokenVerifier;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    @Value("${project.reset.password.token.expiration}")
    private Integer RESET_PASSWORD_TOKEN_EXPIRATION;

    @Value("${project.reset.password.cooldown}")
    private Integer RESET_PASSWORD_COOLDOWN;

    @Value("${project.mail.from}")
    private String MAIL_FROM;

    @Value("${project.name.from}")
    private String NAME_FROM;

    @Value("${project.frontend.url}")
    private String FRONTEND_URL;

    @Value("${project.reset.password.selector.length}")
    private Integer SELECTOR_LENGTH;

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final UserRepository userRepository;
    private final ResetPasswordRequestRepository resetPasswordRequestRepository;
    private final PasswordResetTokenGenerator tokenGenerator;
    private final PasswordResetTokenVerifier tokenVerifier;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void requestPasswordReset(ForgotPasswordRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty())
            return;

        User user = userOptional.get();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.SECOND, -RESET_PASSWORD_COOLDOWN);
        Date coolDownStart = calendar.getTime();

        List<ResetPasswordRequest> recentsRequest = resetPasswordRequestRepository
            .findByUserAndRequestedAtAfter(user, coolDownStart);
        if (!recentsRequest.isEmpty()) {
            return;
        }

        String selector = tokenGenerator.generateRandomString(SELECTOR_LENGTH);
        String hashedToken = tokenGenerator.getHashedToken(selector);

        Calendar expCalendar = Calendar.getInstance();
        expCalendar.setTime(now);
        expCalendar.add(Calendar.SECOND, RESET_PASSWORD_TOKEN_EXPIRATION);
        Date expiresAt = expCalendar.getTime();

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setExpiresAt(expiresAt);
        resetPasswordRequest.setUser(user);
        resetPasswordRequest.setHashedToken(hashedToken);
        resetPasswordRequest.setRequestedAt(now);
        resetPasswordRequest.setSelector(selector);
        resetPasswordRequestRepository.save(resetPasswordRequest);

        sendResetPasswordEmail(
            request.getEmail(),
            String.format("%s/password-update/%s%s", FRONTEND_URL, selector, hashedToken));
    }

    @Override
    public void updatePassword(com.exercise.project.security.request.ResetPasswordRequest request, String fullToken) {
        User user = tokenVerifier.validateTokenAndFetchUser(fullToken);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(new Date());

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void cleanUpExpiredTokens() {
        resetPasswordRequestRepository.deleteExpiredRequests(new Date());
    }

    private void sendResetPasswordEmail(String email, String resetLink) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true);
            mimeHelper.setTo(email);
            mimeHelper.setSubject("Reset password request");
            mimeHelper.setFrom(MAIL_FROM, NAME_FROM);
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("email", email);
            context.put("reset_link", resetLink);
            mimeHelper.setText(
                templateEngine.process("email/reset_password", new Context(Locale.FRENCH, context)),
                true);
            mailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new SendMailException();
        }
    }

}
