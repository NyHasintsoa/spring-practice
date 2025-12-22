package com.exercise.project.security.jwt.revoke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.project.model.entity.auth.RevokedToken;
import com.exercise.project.repository.auth.RevokedTokenRepository;
import com.exercise.project.security.jwt.utils.JwtUtilsInterface;

import io.jsonwebtoken.Claims;

@Service
public class JwtRevokeToken implements JwtRevokeTokenInterface {

    @Autowired
    private RevokedTokenRepository revokedTokenRepository;

    @Autowired
    private JwtUtilsInterface jwtUtils;

    @Override
    public void revokeToken(String token, Boolean isRefreshToken) {
        Claims claims = jwtUtils.getClaimsFromToken(token);
        RevokedToken revokedToken = new RevokedToken();
        revokedToken.setTokenId(claims.getId());
        revokedToken.setExpiryDate(claims.getExpiration());
        revokedToken.setIsRefreshToken(isRefreshToken);
        revokedTokenRepository.save(revokedToken);
    }

    @Override
    public Boolean isTokenRevoked(String tokenId) {
        return revokedTokenRepository.existsByTokenId(tokenId);
    }

}
