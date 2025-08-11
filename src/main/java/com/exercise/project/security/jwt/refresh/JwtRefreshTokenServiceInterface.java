package com.exercise.project.security.jwt.refresh;

import com.exercise.project.entity.auth.User;

public interface JwtRefreshTokenServiceInterface {

    public String createRefreshToken(User user);

    public String refreshAccessToken(String refreshToken);

    public void deleteRefreshToken(String refreshToken);

}
