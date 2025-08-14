package com.exercise.project.service.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.project.entity.auth.User;
import com.exercise.project.exception.UserNotFoundException;
import com.exercise.project.repository.auth.UserRepository;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User persistUser(User user) {
        user.setId(UUID.randomUUID());

        return this.userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(
            () -> new UserNotFoundException("user not found with this email : " + email));
    }

    @Override
    public User lockUserByEmail(String email) {
        User user = this.getByEmail(email);
        user.setAccountNonLocked(false);

        return this.userRepository.save(user);
    }

}
