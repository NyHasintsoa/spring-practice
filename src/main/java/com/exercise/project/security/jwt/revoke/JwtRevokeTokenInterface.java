package com.exercise.project.security.jwt.revoke;

public interface JwtRevokeTokenInterface {

    public void revokeToken(String token, Boolean isRefreshToken);

    public Boolean isTokenRevoked(String tokenId);

}
