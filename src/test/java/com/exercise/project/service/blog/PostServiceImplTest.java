package com.exercise.project.service.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.exercise.project.model.entity.auth.User;
import com.exercise.project.model.entity.blog.Comment;
import com.exercise.project.model.entity.blog.Post;
import com.exercise.project.model.entity.blog.PostLike;
import com.exercise.project.model.entity.blog.Tag;
import com.exercise.project.model.enums.Roles;
import com.exercise.project.repository.blog.CommentRepository;
import com.exercise.project.repository.blog.PostLikeRepository;
import com.exercise.project.repository.blog.PostRepository;
import com.exercise.project.request.blog.CommentRequest;
import com.exercise.project.request.blog.PostRequest;
import com.exercise.project.response.blog.LikePostResponse;
import com.exercise.project.security.user.AuthUserDetails;
import com.exercise.project.service.blog.post.PostServiceImpl;
import com.exercise.project.service.blog.tag.TagService;
import com.exercise.project.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostLikeRepository postLikeRepository;

    @Mock
    private UserService userService;

    @Mock
    private TagService tagService;

    @InjectMocks
    private PostServiceImpl postService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void submitNewPostShouldPersistPostWithSlugAndAuthor() {
        User author = new User();
        author.setEmail("author@example.com");
        author.setFullName("Author Name");
        when(userService.getByEmail("author@example.com")).thenReturn(author);
        when(tagService.findByName("java")).thenReturn(Optional.empty());
        when(tagService.persistTag(any(Tag.class))).thenAnswer(invocation -> {
            Tag tag = invocation.getArgument(0);
            tag.setId(UUID.randomUUID());
            return tag;
        });
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            post.setId(UUID.randomUUID());
            return post;
        });

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new AuthUserDetails(UUID.randomUUID(), "author@example.com", "pass", Set.of(Roles.ROLE_USER)),
                        null,
                        Set.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER"))));

        PostRequest request = new PostRequest("My First Post", "This is a body", null, Set.of("java"));
        Post saved = postService.submitNewPost(request);

        assertThat(saved.getTitle()).isEqualTo("My First Post");
        assertThat(saved.getSlug()).isEqualTo("my-first-post");
        assertThat(saved.getAuthor()).isEqualTo(author);
        assertThat(saved.getTags()).hasSize(1);
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void submitCommentByPostIdShouldSaveCommentWithAuthor() {
        Post post = new Post();
        post.setId(UUID.randomUUID());

        User author = new User();
        author.setEmail("commenter@example.com");

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new AuthUserDetails(UUID.randomUUID(), "commenter@example.com", "pass",
                                Set.of(Roles.ROLE_USER)),
                        null,
                        Set.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER"))));
        when(userService.getByEmail("commenter@example.com")).thenReturn(author);

        Comment comment = postService.submitCommentByPostId(post.getId().toString(),
                new CommentRequest("Nice article!"));

        assertThat(comment.getContent()).isEqualTo("Nice article!");
        assertThat(comment.getPost()).isEqualTo(post);
        assertThat(comment.getAuthor()).isEqualTo(author);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void likePostByPostIdShouldToggleLikeStatus() {
        Post post = new Post();
        post.setId(UUID.randomUUID());
        User author = new User();
        author.setEmail("liked-by@example.com");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new AuthUserDetails(UUID.randomUUID(), "liked-by@example.com", "pass", Set.of(Roles.ROLE_USER)),
                        null,
                        Set.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER"))));
        when(userService.getByEmail("liked-by@example.com")).thenReturn(author);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(postLikeRepository.findByPostAndAuthor(post, author)).thenReturn(Optional.empty());
        when(postLikeRepository.countByPost(post)).thenReturn(1L);

        HttpServletRequest request = org.mockito.Mockito.mock(HttpServletRequest.class);
        when(request.getRemoteHost()).thenReturn("127.0.0.1");

        LikePostResponse response = postService.likePostByPostId(post.getId().toString(), request);

        assertThat(response.getIsLiked()).isTrue();
        assertThat(response.getArticleId()).isEqualTo(post.getId().toString());
        assertThat(response.getLikeCount()).isEqualTo(1L);
        verify(postLikeRepository).save(any(PostLike.class));
    }
}
