package com.exercise.project.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import com.exercise.project.security.user.AuthUserDetails;
import com.github.slugify.Slugify;

public abstract class BaseService<T> {

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

    public abstract Object convertToDto(T data);

    public List<Object> convertAllToDto(List<T> datas) {
        return datas.stream().map(this::convertToDto).toList();
    }

}
