package com.exercise.project.repository.auth;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercise.project.entity.auth.User;

import io.swagger.v3.oas.annotations.Hidden;

import java.util.Optional;

@Hidden
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

}
