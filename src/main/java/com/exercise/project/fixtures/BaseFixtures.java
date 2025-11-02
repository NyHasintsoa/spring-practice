package com.exercise.project.fixtures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.project.fixtures.auth.UserFixtures;
import com.exercise.project.service.database.DatabaseServiceInterface;

@Service
public class BaseFixtures {

    @Autowired
    private UserFixtures userFixtures;

    @Autowired
    private DatabaseServiceInterface databaseService;

    public void init() {
        /** TRUNCATE ALL TABLES */
        databaseService.truncateAllTables();

        /** USERS */
        userFixtures.createMany(3).stream().forEach(userFixtures::store);
        userFixtures.storeAdmin();
    }

}
