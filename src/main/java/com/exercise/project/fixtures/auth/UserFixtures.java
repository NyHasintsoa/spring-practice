package com.exercise.project.fixtures.auth;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exercise.project.model.entity.auth.User;
import com.exercise.project.model.enums.Roles;
import com.exercise.project.fixtures.BaseFixtures;
import com.exercise.project.repository.auth.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFixtures extends BaseFixtures<User> {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User storeAdmin() {
        User user = new User();

        Date createdAt = faker().date().past(3, TimeUnit.DAYS);
        user.setId(UUID.randomUUID());
        user.setFullName("Admin User");
        user.setPhone(faker().phoneNumber().phoneNumber());
        user.setEmail("admin@domain.com");
        user.setPassword(passwordEncoder.encode("Admin@123"));
        Set<Roles> roles = new HashSet<Roles>();
        roles.add(Roles.ROLE_USER);
        roles.add(Roles.ROLE_ADMIN);
        user.setRoles(roles);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(createdAt);
        user.setEnabled(true);
        user.setAccountNonLocked(true);

        return userRepository.save(user);
    }

    @Override
    public User create() {
        User user = new User();

        Date createdAt = faker().date().past(5, TimeUnit.DAYS);
        user.setId(UUID.randomUUID());
        user.setFullName(faker().name().fullName());
        user.setPhone(faker().phoneNumber().phoneNumber());
        user.setEmail(faker().name().username() + "@domain.com");
        user.setPassword(passwordEncoder.encode("Admin@123"));
        Set<Roles> roles = new HashSet<Roles>();
        roles.add(Roles.ROLE_USER);
        user.setRoles(roles);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(createdAt);
        user.setEnabled(true);
        user.setAccountNonLocked(true);

        return user;
    }

    @Override
    public void store(User data) {
        try {
            userRepository.save(data);
        } catch (Exception e) {
            System.out.println(
                "\n##############################################\n" +
                    "USERS \n" +
                    "\n##############################################\n" +
                    e.getMessage() +
                    "\n##############################################\n");
        }
    }

}
