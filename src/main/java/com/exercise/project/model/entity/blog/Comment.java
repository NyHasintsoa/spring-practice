package com.exercise.project.model.entity.blog;

import java.util.Date;
import java.util.UUID;

import com.exercise.project.model.entity.auth.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "blog_comments")
public class Comment {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "content", nullable = false, length = 500, updatable = false)
    private String content;

    @Column(name = "published_at", nullable = false, updatable = false)
    private Date publishedAt;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Post.class, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class, optional = false)
    @JoinColumn(name = "author_id")
    private User author;

}
