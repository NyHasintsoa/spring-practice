package com.exercise.project.security.user;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.exercise.project.enums.Roles;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthUserDetails implements UserDetails {

    private UUID id;

    private String email;

    private String password;

    private Set<Roles> roles;

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
