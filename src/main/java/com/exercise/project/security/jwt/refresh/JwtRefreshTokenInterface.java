package com.exercise.project.security.jwt.refresh;

import com.exercise.project.entity.auth.User;

public interface JwtRefreshTokenInterface {

    public String createRefreshToken(User user);

    public String refreshAccessToken(String refreshToken);

    public void deleteRefreshToken(String refreshToken);

}
