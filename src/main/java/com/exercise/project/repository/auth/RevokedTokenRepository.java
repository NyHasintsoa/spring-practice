package com.exercise.project.repository.auth;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercise.project.model.entity.auth.RevokedToken;

@Repository
public interface RevokedTokenRepository extends JpaRepository<RevokedToken, String> {

    Boolean existsByTokenId(String tokenId);

    void deleteByExpiryDateBefore(Date date);

}
