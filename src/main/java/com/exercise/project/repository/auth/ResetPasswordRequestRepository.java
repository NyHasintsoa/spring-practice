package com.exercise.project.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exercise.project.model.entity.auth.ResetPasswordRequest;

import java.util.Optional;
import java.util.List;
import java.util.Date;
import com.exercise.project.model.entity.auth.User;

public interface ResetPasswordRequestRepository extends JpaRepository<ResetPasswordRequest, Integer> {

    Optional<ResetPasswordRequest> findBySelector(String selector);

    List<ResetPasswordRequest> findByUserAndRequestedAtAfter(User user, Date requestedAt);

    @Modifying
    @Query("DELETE FROM ResetPasswordRequest r WHERE r.expiresAt < :now")
    void deleteExpiredRequests(@Param("now") Date now);

    void deleteByUser(User user);

}
