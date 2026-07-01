package com.exercise.project.security.jwt.refresh;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.exercise.project.model.entity.auth.RefreshToken;
import com.exercise.project.model.entity.auth.User;
import com.exercise.project.exception.InvalidRefreshTokenException;
import com.exercise.project.repository.auth.RefreshTokenRepository;
import com.exercise.project.security.jwt.utils.JwtUtils;
import com.exercise.project.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtRefreshTokenImpl implements JwtRefreshToken {

    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Value("${project.jwt.refresh.token.expiration}")
    private Integer JWT_REFRESH_TOKEN_EXPIRATION;

    @Override
    public String createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setEmail(user.getEmail());
        refreshToken.setExpiryDate(new Date(new Date().getTime() + JWT_REFRESH_TOKEN_EXPIRATION));
        refreshToken.setRefreshToken(jwtUtils.generateRefreshTokenForUser(user));
        refreshTokenRepository.save(refreshToken);

        return refreshToken.getRefreshToken();
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        RefreshToken storedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new InvalidRefreshTokenException());

        if (storedToken.getExpiryDate().before(new Date())) {
            refreshTokenRepository.delete(storedToken);
            throw new InvalidRefreshTokenException();
        }

        User user = userService.getByEmail(storedToken.getEmail());

        return jwtUtils.generateTokenForUser(user);
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository
            .findByRefreshToken(refreshToken)
            .ifPresent(refreshTokenRepository::delete);
    }

}
