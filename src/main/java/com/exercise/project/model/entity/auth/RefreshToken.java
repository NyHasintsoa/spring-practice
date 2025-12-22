package com.exercise.project.model.entity.auth;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "auth_refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "refresh_token", length = 750, nullable = false, unique = true)
    private String refreshToken;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

}
