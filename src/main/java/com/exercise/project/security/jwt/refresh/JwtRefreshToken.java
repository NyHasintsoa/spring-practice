package com.exercise.project.security.jwt.refresh;

import com.exercise.project.model.entity.auth.User;

public interface JwtRefreshToken {

    public String createRefreshToken(User user);

    public String refreshAccessToken(String refreshToken);

    public void deleteRefreshToken(String refreshToken);

}
