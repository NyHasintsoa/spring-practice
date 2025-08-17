package com.exercise.project.service.user;

import com.exercise.project.entity.auth.User;

public interface UserServiceInterface {

    /**
     * Store user in the database
     */
    public User persistUser(User user);

    /**
     * Get User by email address
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

}
