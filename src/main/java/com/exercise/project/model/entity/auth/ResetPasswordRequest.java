package com.exercise.project.model.entity.auth;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "auth_reset_password_requests")
public class ResetPasswordRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "selector", length = 20)
    private String selector;

    @Column(name = "hashed_token", nullable = false, length = 100)
    private String hashedToken;

    @Column(name = "requested_at", nullable = false)
    private Date requestedAt;

    @Column(name = "expires_at", nullable = false)
    private Date expiresAt;

}
