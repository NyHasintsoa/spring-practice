package com.exercise.project.entity.blog;

import java.util.Date;
import java.util.UUID;

import com.exercise.project.entity.auth.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "blog_post_likes")
public class PostLike {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "liked_at", nullable = false)
    private Date likedAt;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Post.class, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class, optional = false)
    @JoinColumn(name = "author_id")
    private User author;

}
