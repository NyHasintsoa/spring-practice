package com.exercise.project.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.exercise.project.security.user.AuthUserDetails;
import com.github.slugify.Slugify;

public abstract class BaseService {

    protected String slugify(String text) {
        return Slugify
            .builder()
            .lowerCase(true)
            .build()
            .slugify(text);
    }

    protected AuthUserDetails getConnectedUser() {
        return ((AuthUserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal());
    }

}
