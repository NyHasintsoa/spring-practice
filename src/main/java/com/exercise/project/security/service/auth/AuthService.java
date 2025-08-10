package com.exercise.project.security.service.auth;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.exercise.project.entity.auth.User;
import com.exercise.project.enums.Roles;
import com.exercise.project.security.jwt.utils.JwtUtilsInterface;
import com.exercise.project.security.request.RegisterRequest;
import com.exercise.project.security.request.SignInRequest;
import com.exercise.project.security.response.JwtResponse;
import com.exercise.project.security.response.UserInfoResponse;
import com.exercise.project.security.user.AuthUserDetails;
import com.exercise.project.service.user.UserServiceInterface;

@Service
public class AuthService implements AuthServiceInterface {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtilsInterface jwtUtils;

    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public JwtResponse signIn(SignInRequest request) {
        Authentication authentication = this.authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = ((User) authentication.getPrincipal());

        return new JwtResponse(
            this.jwtUtils.generateTokenForUser(user),
            new UserInfoResponse(user));
    }

    @Override
    public JwtResponse register(RegisterRequest request) {
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(this.passwordEncoder.encode(request.getPassword()));
        newUser.setFullName(request.getFullname());
        Set<Roles> roles = new HashSet<Roles>();
        roles.add(Roles.ROLE_USER);
        newUser.setRoles(roles);
        newUser.setCreatedAt(new Date());
        newUser.setEnabled(true);
        newUser.setAccountNonLocked(true);
        this.userService.persistUser(newUser);

        return this.signIn(
            new SignInRequest(
                request.getEmail(), request.getPassword()));
    }

    @Override
    public UserInfoResponse getConnectedUserInfo() {
        AuthUserDetails userDetails = ((AuthUserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal());

        return new UserInfoResponse(this.userService.getByEmail(userDetails.getEmail()));
    }

}
