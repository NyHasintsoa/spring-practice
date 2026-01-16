package com.exercise.project.security.service.token;

import com.exercise.project.model.entity.auth.Token;
import com.exercise.project.model.entity.auth.User;

public interface JwtTokenService {

    public void revokeToken(String token);

    public Boolean isTokenRevoked(String tokenId);

    public Token storeToken(String token, User user);

}
