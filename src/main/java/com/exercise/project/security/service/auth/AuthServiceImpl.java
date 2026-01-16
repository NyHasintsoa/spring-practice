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

import com.exercise.project.exception.AlreadyExistsException;
import com.exercise.project.model.dto.auth.UserDto;
import com.exercise.project.model.entity.auth.User;
import com.exercise.project.model.enums.Roles;
import com.exercise.project.security.jwt.utils.JwtUtils;
import com.exercise.project.security.request.ProfileUserRequest;
import com.exercise.project.security.request.RefreshTokenRequest;
import com.exercise.project.security.request.RegisterRequest;
import com.exercise.project.security.request.SignInRequest;
import com.exercise.project.security.response.JwtResponse;
import com.exercise.project.security.response.UserInfoResponse;
import com.exercise.project.security.service.email.verifier.EmailVerifierService;
import com.exercise.project.security.service.token.JwtTokenService;
import com.exercise.project.security.user.AuthUserDetails;
import com.exercise.project.service.BaseService;
import com.exercise.project.service.user.UserService;

@Service
public class AuthServiceImpl extends BaseService<User> implements AuthService {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailVerifierService emailVerifierService;

    @Value("${project.jwt.bearer.prefix}")
    private String BEARER_PREFIX;

    @Value("${project.frontend.url}")
    private String FRONTEND_URL;

    @Override
    public JwtResponse signIn(SignInRequest request) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = ((User) authentication.getPrincipal());
        String token = jwtUtils.generateTokenForUser(user);
        jwtTokenService.storeToken(token, user);

        return new JwtResponse(
            token,
            "Bearer",
            "Refresh Token Here",
            new UserInfoResponse(user));
    }

    @Override
    public void register(RegisterRequest request) {
        if (userService.emailIsUsed(request.getEmail()))
            throw new AlreadyExistsException("EMAIL_ALREADY_EXIST");
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setFullName(request.getFullname());
        Set<Roles> roles = new HashSet<Roles>();
        roles.add(Roles.ROLE_USER);
        newUser.setRoles(roles);
        newUser.setCreatedAt(new Date());
        newUser.setEnabled(false);
        newUser.setAccountNonLocked(true);

        emailVerifierService.sendEmailConfirmation(newUser);
        userService.persistUser(newUser);
    }

    @Override
    public void confirmUserByToken(String token) {
        emailVerifierService.handleEmailConfirmation(token);
    }

    @Override
    public UserInfoResponse getConnectedUserInfo() {
        AuthUserDetails userDetails = getConnectedUser();

        return new UserInfoResponse(userService.getByEmail(userDetails.getEmail()));
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest request) {
        return new JwtResponse(
            "Refresh Token Here",
            "Bearer",
            null,
            null
        );
    }

    @Override
    public URI buildRedirectUrl() {
        return URI.create(FRONTEND_URL + "/sign-in");
    }

    @Override
    public UserInfoResponse updateUserProfile(ProfileUserRequest request) {
        AuthUserDetails userDetails = getConnectedUser();

        User user = userService.getByEmail(userDetails.getEmail());
        user.setFullName(request.getFirstname() + " " + request.getLastname());
        user.setPhone(request.getPhone());
        User updatedUser = userService.saveUser(user);

        return new UserInfoResponse(updatedUser);
    }

    @Override
    public UserDto convertToDto(User data) {
        return userService.convertToDto(data);
    }

}
