package com.exercise.project.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.core.io.ClassPathResource;

public final class KeyPairUtil {

    private static final String PRIVATE_KEY_PATH = "jwt/private.pem";
    private static final String PUBLIC_KEY_PATH = "jwt/public.pem";

    public static PrivateKey getPrivateKey() {
        try {
            String key = readKeyFile(PRIVATE_KEY_PATH);
            String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
            byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePrivate(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to load private key", e);
        }
    }

    public static PublicKey getPublicKey() {
        try {
            String key = readKeyFile(PUBLIC_KEY_PATH);
            String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
            byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePublic(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to load public key", e);
        }
    }

    private static String readKeyFile(String classpathLocation) {
        try {
            ClassPathResource resource = new ClassPathResource(classpathLocation);
            try (InputStream inputStream = resource.getInputStream()) {
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new RuntimeException("Key file not found in classpath: " + classpathLocation, e);
        }
    }
}
