package com.exercise.project.security.service.reset.token.generator;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenGenerator implements PasswordResetTokenGeneratorInterface {

    @Value("${project.secret.key}")
    private String PROJECT_SECRET_KEY;

    @Override
    public String generateRandomString(Integer length) {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();

        while (sb.length() < length) {
            int size = length - sb.length();
            byte[] bytes = new byte[size];
            random.nextBytes(bytes);
            String base64 = Base64.getEncoder().encodeToString(bytes);
            String cleaned = base64.replaceAll("[/+=]", "");

            sb.append(cleaned, 0, Math.min(cleaned.length(), size));
        }

        return sb.toString();
    }

    @Override
    public String getHashedToken(String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(PROJECT_SECRET_KEY.getBytes(), "HmacSHA256");
            hmac.init(secretKey);

            byte[] hashBytes = hmac.doFinal(data.getBytes());

            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return "";
        }
    }

}
