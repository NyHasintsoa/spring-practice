package com.exercise.project.security.service.redis.login;

public interface RedisLoginAttemptServiceInterface {

    public Long loginFailed(String email);

    public void loginSucceeded(String email);

}
