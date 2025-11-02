package com.exercise.project.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.exercise.project.security.user.AuthUserDetails;

public abstract class BaseService {

    protected AuthUserDetails getConnectedUser() {
        return ((AuthUserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal());
    }

}
