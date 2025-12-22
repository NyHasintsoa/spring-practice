package com.exercise.project.fixtures.blog;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.project.model.entity.auth.User;
import com.exercise.project.model.entity.blog.Comment;
import com.exercise.project.model.entity.blog.Post;
import com.exercise.project.model.entity.blog.Tag;
import com.exercise.project.fixtures.BaseFixtures;
import com.exercise.project.repository.auth.UserRepository;
import com.exercise.project.repository.blog.PostRepository;
import com.exercise.project.repository.blog.TagRepository;
import com.exercise.project.util.RandomArrayUtil;

@Service
public class PostFixtures extends BaseFixtures<Post> {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    private List<Tag> tags() {
        return tagRepository.findAll();
    }

    private List<User> users() {
        return userRepository.findAll();
    }

    private Collection<Tag> randomTags() {
        return RandomArrayUtil.<Tag>getRandomUniqueElements(tags(), faker().random().nextInt(3, 5));
    }

    private User randomUser() {
        return RandomArrayUtil.<User>getRandomElement(users());
    }

    @Override
    public Post create() {
        Post post = new Post();

        Date createdAt = faker().date().past(3, TimeUnit.DAYS);

        post.setId(UUID.randomUUID());
        String postTitle = faker().book().title();
        post.setTitle(postTitle);
        post.setContent(faker().lorem().sentence());
        post.setSlug(slugify(postTitle));
        post.setPublishedAt(createdAt);
        post.setUpdatedAt(createdAt);
        post.setTags(randomTags());
        post.setAuthor(randomUser());

        post.setComments(comments().stream().map((postItem) -> {
            postItem.setPost(post);
            return postItem;
        }).toList());

        return post;
    }

    @Override
    public void store(Post data) {
        try {
            postRepository.save(data);
        } catch (Exception e) {
            System.out.println(
                "\n##############################################\n" +
                    "POSTS \n" +
                    "\n##############################################\n" +
                    e.getMessage() +
                    "\n##############################################\n");
        }
    }

    private Collection<Comment> comments() {
        Collection<Comment> comments = new HashSet<Comment>();

        for (int i = 0; i < faker().random().nextInt(1, 5); i++) {
            Comment comment = new Comment();
            comment.setId(UUID.randomUUID());
            comment.setAuthor(randomUser());
            comment.setContent(faker().lorem().sentence());
            comment.setPublishedAt(faker().date().past(3, TimeUnit.DAYS));

            comments.add(comment);
        }

        return comments;
    }

}
