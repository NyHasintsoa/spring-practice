package com.exercise.project.repository.blog;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.project.model.entity.blog.Tag;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    Optional<Tag> findByName(String name);

}
