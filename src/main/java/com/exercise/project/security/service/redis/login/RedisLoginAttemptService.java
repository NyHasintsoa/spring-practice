package com.exercise.project.security.service.redis.login;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.exercise.project.exception.UserAccountLockedException;
import com.exercise.project.exception.UserNotFoundException;
import com.exercise.project.service.user.UserServiceInterface;

@Service
public class RedisLoginAttemptService implements RedisLoginAttemptServiceInterface {

    @Value("${project.login.max.attempts}")
    private Integer MAX_ATTEMPTS;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserServiceInterface userService;

    private ValueOperations<String, Object> getValueOperations() {
        return redisTemplate.opsForValue();
    }

    @Override
    public Long loginFailed(String email) {
        String attemptKey = "login_attempts:" + email;
        Long attempts = getValueOperations().increment(attemptKey);

        if (attempts == null) {
            attempts = 1L;
            redisTemplate.expire(attemptKey, Duration.ofHours(1));
        }

        if (attempts >= MAX_ATTEMPTS) {
            try {
                userService.lockUserByEmail(email);
                throw new UserAccountLockedException();
            } catch (UserNotFoundException e) {
            }
        }

        return attempts;
    }

    @Override
    public void loginSucceeded(String email) {

    }

}
