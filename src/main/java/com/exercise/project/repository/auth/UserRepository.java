package com.exercise.project.repository.auth;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.project.model.entity.auth.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
