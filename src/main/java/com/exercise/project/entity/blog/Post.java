package com.exercise.project.entity.blog;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import com.exercise.project.entity.auth.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "blog_posts")
public class Post {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "slug", length = 100, nullable = false, unique = true, updatable = false)
    private String slug;

    @Column(name = "title", length = 250, nullable = false)
    private String title;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @Column(name = "header_image_path", length = 200, nullable = true)
    private String headerImagePath;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "blog_post_tags",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Collection<Tag> tags = new HashSet<Tag>();

    @OneToMany(targetEntity = Comment.class, orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<Comment> comments = new HashSet<Comment>();

    @OneToMany(targetEntity = PostLike.class, orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<PostLike> likes = new HashSet<PostLike>();

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.DETACH)
    @JoinColumn(nullable = false, name = "author_id")
    private User author;

    @Column(name = "published_at", nullable = false)
    private Date publishedAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

}
