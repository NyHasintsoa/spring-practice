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

    private String fullName;

    private String phone;

    private Set<Roles> roles;

    private Date createdAt;

    public UserInfoResponse(User user) {
        id = user.getId();
        email = user.getEmail();
        fullName = user.getFullName();
        phone = user.getPhone();
        roles = user.getRoles();
        createdAt = user.getCreatedAt();
    }

}
