package com.exercise.project.config.packages;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(redisNamespace = "spring:session:projectapplication")
public class SessionConfig {

}
