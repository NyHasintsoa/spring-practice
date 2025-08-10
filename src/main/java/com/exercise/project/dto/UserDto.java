package com.exercise.project.dto;

import java.util.Set;
import java.util.UUID;

import com.exercise.project.enums.Roles;

import lombok.Getter;

@Getter
public class UserDto {

    private UUID id;

    private String email;

    private String username;

    private String fullname;

    private Set<Roles> roles;

}
