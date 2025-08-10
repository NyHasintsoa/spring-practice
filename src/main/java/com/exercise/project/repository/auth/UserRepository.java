package com.exercise.project.repository.auth;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.project.entity.auth.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
