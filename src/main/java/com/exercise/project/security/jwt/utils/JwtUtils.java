package com.exercise.project.security.jwt.utils;

import com.exercise.project.model.entity.auth.User;
import com.exercise.project.security.user.AuthUserDetails;

import io.jsonwebtoken.Claims;

public interface JwtUtils {

    public String generateTokenForUser(User user);

    public String generateRefreshTokenForUser(User user);

    public String getUsernameFromToken(String token);

    public Claims getClaimsFromToken(String token);

    public Boolean validateToken(String token);

    public AuthUserDetails buildUserDetailsFromToken(String token);

    public String extractTokenId(String token);

}
