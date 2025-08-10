package com.exercise.project.entity.auth;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.exercise.project.enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(unique = true, nullable = false, updatable = false, length = 200)
    private String email;

    @Column(name = "user_name", unique = true, nullable = false, length = 200)
    private String username;

    @Column(name = "full_name", nullable = true, length = 200)
    private String fullName;

    @Column(nullable = false, length = 200)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Roles> roles;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

}
