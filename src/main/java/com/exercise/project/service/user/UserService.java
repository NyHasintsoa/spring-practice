package com.exercise.project.service.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.project.dto.UserDto;
import com.exercise.project.entity.auth.User;
import com.exercise.project.exception.UserNotFoundException;
import com.exercise.project.repository.auth.UserRepository;
import com.exercise.project.service.BaseService;

@Service
public class UserService extends BaseService<User> implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User persistUser(User user) {
        user.setId(UUID.randomUUID());

        return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
            () -> new UserNotFoundException("user not found with this email : " + email));
    }

    @Override
    public User lockUserByEmail(String email) {
        User user = getByEmail(email);
        user.setAccountNonLocked(false);

        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDto convertToDto(User data) {
        return new UserDto(
            data.getId(),
            data.getEmail(),
            data.getUsername(),
            data.getFullName(),
            data.getRoles()
        );
    }

}
