package com.exercise.project.model.dto.auth;

import java.util.Set;
import java.util.UUID;

import com.exercise.project.model.entity.auth.User;
import com.exercise.project.model.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {

    private UUID id;

    private String email;

    private String username;

    private String fullname;

    private Set<Roles> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.fullname = user.getFullName();
        this.roles = user.getRoles();
    }

}
