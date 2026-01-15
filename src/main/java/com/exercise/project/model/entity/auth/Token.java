package com.exercise.project.model.entity.auth;

import java.util.Date;

import com.exercise.project.model.enums.TokenType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "auth_tokens")
public class Token {

    @Id
    @Column(name = "token_id", length = 250, nullable = false, unique = true)
    private String tokenId;

    @Column(name = "expiry_date", nullable = false, updatable = false)
    private Date expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false)
    private TokenType tokenType;

    @Column(name = "revoked_at")
    private Date revokedAt;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.DETACH)
    @JoinColumn(nullable = false, name = "user_id", updatable = false)
    private User user;

}
