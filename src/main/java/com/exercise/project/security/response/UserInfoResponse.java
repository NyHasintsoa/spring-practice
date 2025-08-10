package com.exercise.project.security.response;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.exercise.project.entity.auth.User;
import com.exercise.project.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {

    private UUID id;

    private String email;

    private String username;

    private String fullName;

    private Set<Roles> roles;

    private Date createdAt;

    public UserInfoResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.roles = user.getRoles();
        this.createdAt = user.getCreatedAt();
    }

}
