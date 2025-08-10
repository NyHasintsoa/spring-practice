package com.exercise.project.service.user;

import com.exercise.project.entity.auth.User;

public interface UserServiceInterface {

    /**
     * Store user in the database
     */
    public User persistUser(User user);

    public User getByEmail(String email);

}
