package com.exercise.project.repository.blog;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.project.entity.blog.Tag;

public interface TagRepository extends JpaRepository<Tag, UUID> {

}
