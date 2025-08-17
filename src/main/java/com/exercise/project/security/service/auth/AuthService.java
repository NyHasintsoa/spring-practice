package com.exercise.project.security.service.auth;

import java.net.URI;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.exercise.project.entity.auth.User;
import com.exercise.project.enums.Roles;
import com.exercise.project.security.jwt.refresh.JwtRefreshTokenInterface;
import com.exercise.project.security.jwt.revoke.JwtRevokeTokenInterface;
import com.exercise.project.security.jwt.utils.JwtUtilsInterface;
import com.exercise.project.security.request.RefreshTokenRequest;
import com.exercise.project.security.request.RegisterRequest;
import com.exercise.project.security.request.SignInRequest;
import com.exercise.project.security.response.JwtResponse;
import com.exercise.project.security.response.UserInfoResponse;
import com.exercise.project.security.service.email.verifier.EmailVerifierServiceInterface;
import com.exercise.project.security.user.AuthUserDetails;
import com.exercise.project.service.user.UserServiceInterface;

@Service
public class AuthService implements AuthServiceInterface {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtilsInterface jwtUtils;

    @Autowired
    private JwtRefreshTokenInterface jwtRefreshToken;

    @Autowired
    private JwtRevokeTokenInterface jwtRevokeToken;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailVerifierServiceInterface emailVerifierService;

    @Value("${project.jwt.bearer.prefix}")
    private String BEARER_PREFIX;

    @Value("${project.frontend.url}")
    private String FRONTEND_URL;

    @Override
    public JwtResponse signIn(SignInRequest request) {
        Authentication authentication = this.authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = ((User) authentication.getPrincipal());

        return new JwtResponse(
            this.jwtUtils.generateTokenForUser(user),
            this.jwtRefreshToken.createRefreshToken(user),
            new UserInfoResponse(user));
    }

    @Override
    public void register(RegisterRequest request) {
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(this.passwordEncoder.encode(request.getPassword()));
        newUser.setFullName(request.getFullname());
        Set<Roles> roles = new HashSet<Roles>();
        roles.add(Roles.ROLE_USER);
        newUser.setRoles(roles);
        newUser.setCreatedAt(new Date());
        newUser.setEnabled(false);
        newUser.setAccountNonLocked(true);

        this.emailVerifierService.sendEmailConfirmation(newUser);
        this.userService.persistUser(newUser);
    }

    @Override
    public void confirmUserByToken(String token) {
        this.emailVerifierService.handleEmailConfirmation(token);
    }

    @Override
    public UserInfoResponse getConnectedUserInfo() {
        AuthUserDetails userDetails = ((AuthUserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal());

        return new UserInfoResponse(this.userService.getByEmail(userDetails.getEmail()));
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest request) {
        String newAccessToken = this.jwtRefreshToken.refreshAccessToken(request.getRefreshToken());
        return new JwtResponse(newAccessToken, newAccessToken, null);
    }

    @Override
    public void logout(String authHeader) {
        String token = authHeader.substring(BEARER_PREFIX.length());
        this.jwtRevokeToken.revokeToken(token, false);
    }

    @Override
    public URI buildRedirectUrl() {
        return URI.create(FRONTEND_URL + "/auth/sign-in");
    }

}
