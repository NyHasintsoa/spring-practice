package com.exercise.project.security.service.token;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.exercise.project.model.entity.auth.Token;
import com.exercise.project.model.entity.auth.User;
import com.exercise.project.model.enums.TokenType;
import com.exercise.project.repository.auth.TokenRepository;
import com.exercise.project.security.jwt.utils.JwtUtils;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final TokenRepository tokenRepository;
    private final JwtUtils jwtUtils;

    @Override
    public void revokeToken(String token) {
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Optional<Token> isRevokedToken = tokenRepository.findByTokenId(claims.getId());
        if (isRevokedToken.isPresent()) {
            Token revokedToken = isRevokedToken.get();
            if (revokedToken.getRevokedAt() == null) {
                revokedToken.setRevokedAt(new Date());
                tokenRepository.save(revokedToken);
            }
        }
    }

    @Override
    public Boolean isTokenRevoked(String tokenId) {
        Optional<Token> istoken = tokenRepository.findByTokenId(tokenId);
        if (istoken.isEmpty())
            return true;
        return istoken.get().getRevokedAt() != null;
    }

    @Override
    public Token storeToken(String token, User user) {
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Token storeToken = new Token();
        storeToken.setTokenId(claims.getId());
        storeToken.setExpiryDate(claims.getExpiration());
        storeToken.setTokenType(TokenType.BEARER_TOKEN);
        storeToken.setUser(user);

        return tokenRepository.save(storeToken);
    }

}
