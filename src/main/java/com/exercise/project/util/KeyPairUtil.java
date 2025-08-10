package com.exercise.project.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.util.ResourceUtils;

public final class KeyPairUtil {

    private static String privateKeyPath = "src/main/java/com/exercise/project/config/jwt/private.pem";

    private static String publicKeyPath = "src/main/java/com/exercise/project/config/jwt/public.pem";

    public static PrivateKey getPrivateKey() {
        try {
            String key = readKeyFile(privateKeyPath);
            String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
            byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePrivate(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static PublicKey getPublicKey() {
        try {
            String key = readKeyFile(publicKeyPath);
            String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
            byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePublic(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String readKeyFile(String resourceLocation) {
        try {
            File file;
            file = ResourceUtils.getFile(resourceLocation);

            return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
