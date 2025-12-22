package com.exercise.project.service.user;

import java.util.List;

import com.exercise.project.model.dto.UserDto;
import com.exercise.project.model.entity.auth.User;
import com.exercise.project.exception.UserNotFoundException;

public interface UserServiceInterface {

    /**
     * Store user in the database
     */
    public User persistUser(User user);

    /**
     * Get User by email address
     * 
     * @throws UserNotFoundException
     */
    public User getByEmail(String email);

    /**
     * Lock User by email
     */
    public User lockUserByEmail(String email);

    /**
     * Save Existing user to the database
     */
    public User saveUser(User user);

    /**
     * Convert User To DTO
     */
    public UserDto convertToDto(User data);

    /**
     * Convert All Users to DTO
     */
    public List<Object> convertAllToDto(List<User> datas);

}
