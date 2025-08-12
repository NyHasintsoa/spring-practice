package com.exercise.project.security.jwt.refresh;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.exercise.project.entity.auth.RefreshToken;
import com.exercise.project.entity.auth.User;
import com.exercise.project.exception.InvalidRefreshTokenException;
import com.exercise.project.repository.auth.RefreshTokenRepository;
import com.exercise.project.security.jwt.utils.JwtUtilsInterface;
import com.exercise.project.service.user.UserServiceInterface;

@Service
public class JwtRefreshToken implements JwtRefreshTokenInterface {

    @Autowired
    private JwtUtilsInterface jwtUtils;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserServiceInterface userService;

    @Value("${project.jwt.refresh.token.expiration}")
    private Integer JWT_REFRESH_TOKEN_EXPIRATION;

    @Override
    public String createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setEmail(user.getEmail());
        refreshToken.setExpiryDate(new Date(new Date().getTime() + this.JWT_REFRESH_TOKEN_EXPIRATION));
        refreshToken.setRefreshToken(jwtUtils.generateRefreshTokenForUser(user));
        this.refreshTokenRepository.save(refreshToken);

        return refreshToken.getRefreshToken();
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        if (!this.jwtUtils.validateToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        RefreshToken storedToken = this.refreshTokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new InvalidRefreshTokenException());

        if (storedToken.getExpiryDate().before(new Date())) {
            refreshTokenRepository.delete(storedToken);
            throw new InvalidRefreshTokenException();
        }

        User user = this.userService.getByEmail(storedToken.getEmail());

        return this.jwtUtils.generateTokenForUser(user);
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        this.refreshTokenRepository
            .findByRefreshToken(refreshToken)
            .ifPresent(refreshTokenRepository::delete);
    }

}
