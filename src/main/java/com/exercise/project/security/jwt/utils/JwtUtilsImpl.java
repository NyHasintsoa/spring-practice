package com.exercise.project.security.jwt.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.exercise.project.model.entity.auth.User;
import com.exercise.project.model.enums.Roles;
import com.exercise.project.security.user.AuthUserDetails;
import com.exercise.project.util.KeyPairUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtUtilsImpl implements JwtUtils {

    @Value("${project.jwt.token.expiration}")
    private Integer JWT_TOKEN_EXPIRATION;

    @Value("${project.jwt.refresh.token.expiration}")
    private Integer JWT_REFRESH_TOKEN_EXPIRATION;

    @Override
    public String generateTokenForUser(User user) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", user.getId());
        claims.put(
            "roles",
            user.getRoles());

        return doGenerateToken(claims, user.getEmail(), JWT_TOKEN_EXPIRATION);
    }

    @Override
    public String generateRefreshTokenForUser(User user) {
        return doGenerateToken(null, user.getEmail(), JWT_REFRESH_TOKEN_EXPIRATION);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, Integer expirationTime) {
        return Jwts.builder()
            .issuer(subject)
            .subject(subject)
            .claims(claims)
            .issuedAt(new Date())
            .expiration(new Date(new Date().getTime() + expirationTime))
            .id(UUID.randomUUID().toString())
            .signWith(KeyPairUtil.getPrivateKey())
            .compact();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
            .verifyWith(KeyPairUtil.getPublicKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    @Override
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
            .verifyWith(KeyPairUtil.getPublicKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    @Override
    public Boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(KeyPairUtil.getPublicKey())
                .build()
                .parseSignedClaims(token);

            return true;
        } catch (
            ExpiredJwtException
            | UnsupportedJwtException
            | MalformedJwtException
            | SecurityException
            | IllegalArgumentException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public AuthUserDetails buildUserDetailsFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        UUID id = UUID.fromString(claims.get("id", String.class));
        List<String> roles = claims.get("roles", List.class);

        return new AuthUserDetails(
            id,
            getUsernameFromToken(token),
            claims.get("password", String.class),
            roles.stream().map((role) -> Roles.valueOf(role)).collect(Collectors.toSet()));
    }

    @Override
    public String extractTokenId(String token) {
        return getClaimsFromToken(token).getId();
    }

}
