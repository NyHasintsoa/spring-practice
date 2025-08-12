package com.exercise.project.security.jwt.keypair;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class JwtKeyPair implements JwtKeyPairInterface {

    @Value("classpath:com/exercise/project/config/jwt/private.pem")
    private String PRIVATE_KEY_PATH;

    @Value("classpath:com/exercise/project/config/jwt/public.pem")
    private String PUBLIC_KEY_PATH;

    @Override
    public PrivateKey getPrivateKey() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public PublicKey getPublicKey() {
        String key;
        try {
            key = readKeyFile(this.PUBLIC_KEY_PATH);
            String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
            byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String readKeyFile(String resourceLocation) throws Exception {
        try {
            if (resourceLocation.startsWith("classpath:")) {
                // Pour le classpath (durant l'exécution)
                ClassPathResource resource = new ClassPathResource(resourceLocation.substring(10));
                try (InputStream inputStream = resource.getInputStream()) {
                    return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                }
            } else {
                File file = ResourceUtils.getFile(resourceLocation);
                return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read key file: " + resourceLocation, e);
        }
    }

}
