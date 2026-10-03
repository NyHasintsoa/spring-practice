package com.exercise.project.functional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.exercise.project.model.dto.blog.TagDto;
import com.exercise.project.model.entity.auth.User;
import com.exercise.project.model.entity.blog.Post;
import com.exercise.project.model.entity.blog.Tag;
import com.exercise.project.model.enums.Roles;
import com.exercise.project.security.response.JwtResponse;
import com.exercise.project.security.response.UserInfoResponse;
import com.exercise.project.security.service.auth.AuthService;
import com.exercise.project.service.blog.post.PostService;
import com.exercise.project.service.blog.tag.TagService;

@SpringBootTest
class ApiFeatureFunctionalTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private TagService tagService;

    @MockitoBean
    private PostService postService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = "USER")
    void shouldSignInAndReturnToken() throws Exception {
        when(authService.signIn(any())).thenReturn(new JwtResponse(
            "jwt-token",
            "Bearer",
            null,
            new UserInfoResponse(UUID.randomUUID(), "user@example.com", "Jane Doe", null, Set.of(Roles.ROLE_USER), null)
        ));

        mockMvc.perform(post("/api/auth/sign-in")
                .contentType(APPLICATION_JSON)
                .content("{\"email\":\"user@example.com\",\"password\":\"secret123\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.token").value("jwt-token"));
    }

    @Test
    void shouldRegisterUser() throws Exception {
        mockMvc.perform(post("/api/auth/sign-up")
                .contentType(APPLICATION_JSON)
                .content("{\"email\":\"user@example.com\",\"fullname\":\"Jane Doe\",\"password\":\"secret123\",\"confirmPassword\":\"secret123\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void shouldReturnTags() throws Exception {
        Tag tag = new Tag();
        tag.setId(UUID.randomUUID());
        tag.setName("java");
        when(tagService.getAll()).thenReturn(List.of(tag));
        when(tagService.convertAllToDto(any())).thenReturn(List.of(new TagDto(tag)));

        mockMvc.perform(get("/api/tags"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data[0].name").value("java"));
    }

    @Test
    void shouldReturnPosts() throws Exception {
        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setTitle("Hello Spring");
        post.setContent("Body");
        post.setSlug("hello-spring");
        User author = new User();
        author.setEmail("author@example.com");
        post.setAuthor(author);
        when(postService.getPaginatedPosts(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(post)));
        when(postService.convertToDto(any())).thenReturn(new com.exercise.project.model.dto.blog.PostDto(post));

        mockMvc.perform(get("/api/blog/posts"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
    }
}
