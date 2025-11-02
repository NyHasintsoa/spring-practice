package com.exercise.project.fixtures.auth;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exercise.project.entity.auth.User;
import com.exercise.project.enums.Roles;
import com.exercise.project.service.user.UserServiceInterface;
import com.github.javafaker.Faker;

@Service
public class UserFixtures {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceInterface userService;

    private Faker faker;

    public UserFixtures() {
        faker = Faker.instance(Locale.ENGLISH);
    }

    public List<User> createMany(Integer number) {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < number; i++) {
            users.add(create());
        }

        return users;
    }

    public User storeAdmin() {
        User user = new User();

        Date createdAt = faker.date().past(3, TimeUnit.DAYS);
        user.setId(UUID.randomUUID());
        user.setFullName("Admin User");
        user.setPhone(faker.phoneNumber().phoneNumber());
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

        return userService.saveUser(user);
    }

    public User create() {
        User user = new User();

        Date createdAt = faker.date().past(5, TimeUnit.DAYS);
        user.setId(UUID.randomUUID());
        user.setFullName(faker.name().fullName());
        user.setPhone(faker.phoneNumber().phoneNumber());
        user.setEmail(faker.name().username() + "@domain.com");
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

    public void store(User user) {
        try {
            userService.saveUser(user);
        } catch (Exception e) {
        }
    }

}
