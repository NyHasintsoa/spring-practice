package com.exercise.project.model.entity.auth;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "auth_revoked_tokens")
public class RevokedToken {

    @Id
    @Column(name = "token_id", length = 250, nullable = false, unique = true)
    private String tokenId;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @Column(nullable = false)
    private Boolean isRefreshToken;

}
