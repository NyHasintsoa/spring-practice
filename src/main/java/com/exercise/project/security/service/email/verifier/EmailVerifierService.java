package com.exercise.project.security.service.email.verifier;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.exercise.project.model.entity.auth.User;
import com.exercise.project.exception.SendMailException;
import com.exercise.project.service.user.UserServiceInterface;

import io.jsonwebtoken.Jwts;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailVerifierService implements EmailVerifierServiceInterface {

    @Value("${project.mail.from}")
    private String MAIL_FROM;

    @Value("${project.name.from}")
    private String NAME_FROM;

    @Value("${project.backend.url}")
    private String BACKEND_URL;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private UserServiceInterface userService;

    private Integer TOKEN_EXPIRATION_TIME = 3600000;

    @Override
    public void sendEmailConfirmation(User user) {
        try {
            String userEmail = user.getEmail();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true);
            mimeHelper.setTo(userEmail);
            mimeHelper.setSubject("Email Confirmation Message");
            mimeHelper.setFrom(MAIL_FROM, NAME_FROM);
            Map<String, Object> context = new HashMap<String, Object>();
            String token = generateTokenToConfirmEmail(userEmail);
            context.put("token", token);
            context.put("email", userEmail);
            context.put("backend_url", BACKEND_URL);
            mimeHelper.setText(
                templateEngine.process("email/email_verifiy", new Context(Locale.FRENCH, context)),
                true);
            mailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new SendMailException();
        }
    }

    @Override
    public void handleEmailConfirmation(String token) {
        String email = getEmailFromToken(token);
        User user = userService.getByEmail(email);
        user.setEnabled(true);
        userService.saveUser(user);
    }

    private String generateTokenToConfirmEmail(String email) {
        return Jwts.builder()
            .issuer(MAIL_FROM)
            .subject(email)
            .claim("email", email)
            .issuedAt(new Date())
            .expiration(new Date(new Date().getTime() + TOKEN_EXPIRATION_TIME))
            .compact();
    }

    private String getEmailFromToken(String token) {
        return (String) Jwts.parser()
            .unsecured()
            .build()
            .parseUnsecuredClaims(token)
            .getPayload()
            .getSubject();
    }

}
