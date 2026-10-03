package com.exercise.project.security.service.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.exercise.project.model.entity.auth.User;
import com.exercise.project.model.enums.Roles;
import com.exercise.project.security.jwt.utils.JwtUtils;
import com.exercise.project.security.request.ProfileUserRequest;
import com.exercise.project.security.request.RegisterRequest;
import com.exercise.project.security.request.SignInRequest;
import com.exercise.project.security.response.JwtResponse;
import com.exercise.project.security.response.UserInfoResponse;
import com.exercise.project.security.service.email.verifier.EmailVerifierService;
import com.exercise.project.security.service.token.JwtTokenService;
import com.exercise.project.security.user.AuthUserDetails;
import com.exercise.project.service.user.UserService;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private EmailVerifierService emailVerifierService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authService, "FRONTEND_URL", "http://localhost:3000");
        SecurityContextHolder.clearContext();
    }

    @Test
    void signInShouldGenerateTokenAndPersistIt() {
        SignInRequest request = new SignInRequest("user@example.com", "secret123");
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("user@example.com");
        user.setFullName("Jane Doe");
        user.setRoles(Set.of(Roles.ROLE_USER));
        user.setCreatedAt(new Date());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        when(authManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtils.generateTokenForUser(user)).thenReturn("jwt-token");

        JwtResponse response = authService.signIn(request);

        assertThat(response.getToken()).isEqualTo("jwt-token");
        assertThat(response.getType()).isEqualTo("Bearer");
        assertThat(response.getUserInfoResponse()).isNotNull();
        assertThat(response.getUserInfoResponse().getEmail()).isEqualTo("user@example.com");
        verify(jwtTokenService).storeToken("jwt-token", user);
    }

    @Test
    void registerShouldEncodePasswordAndSendConfirmationMail() {
        RegisterRequest request = new RegisterRequest(
            "new-user@example.com",
            "New User",
            "secret123",
            "secret123"
        );
        when(userService.emailIsUsed(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("secret123")).thenReturn("encoded-password");

        authService.register(request);

        verify(emailVerifierService).sendEmailConfirmation(any(User.class));
        verify(userService).persistUser(any(User.class));
    }

    @Test
    void updateUserProfileShouldPersistFullNameAndPhone() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setFullName("Old Name");
        user.setPhone("000");

        when(userService.getByEmail("user@example.com")).thenReturn(user);
        when(userService.saveUser(user)).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                new AuthUserDetails(UUID.randomUUID(), "user@example.com", "pass", Set.of(Roles.ROLE_USER)),
                null,
                Set.of(new SimpleGrantedAuthority("ROLE_USER"))
            )
        );

        ProfileUserRequest request = new ProfileUserRequest("Jane", "Doe", "123456");
        UserInfoResponse response = authService.updateUserProfile(request);

        assertThat(response.getFullName()).isEqualTo("Jane Doe");
        assertThat(response.getPhone()).isEqualTo("123456");
        verify(userService).saveUser(user);
    }

    @Test
    void buildRedirectUrlShouldPointToFrontendSignInPage() {
        assertThat(authService.buildRedirectUrl().toString()).isEqualTo("http://localhost:3000/sign-in");
    }
}
