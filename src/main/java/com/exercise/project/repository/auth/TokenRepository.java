package com.exercise.project.repository.auth;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercise.project.model.entity.auth.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    Optional<Token> findByTokenId(String tokenId);

    void deleteByExpiryDateBefore(Date date);

}
