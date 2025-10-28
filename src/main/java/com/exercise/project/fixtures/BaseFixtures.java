package com.exercise.project.fixtures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.project.fixtures.auth.UserFixtures;

@Service
public class BaseFixtures {

    @Autowired
    private UserFixtures userFixtures;

    public void store() {
        userFixtures.createMany(3).stream().forEach((user) -> {
            System.out.println("User : " + user.getEmail());
        });
    }

}
