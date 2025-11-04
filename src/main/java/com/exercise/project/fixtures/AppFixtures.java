package com.exercise.project.fixtures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.project.fixtures.auth.UserFixtures;
import com.exercise.project.fixtures.blog.PostFixtures;
import com.exercise.project.fixtures.blog.TagFixtures;
import com.exercise.project.service.database.DatabaseServiceInterface;

@Service
public class AppFixtures {

    @Autowired
    private UserFixtures userFixtures;

    @Autowired
    private TagFixtures tagFixtures;

    @Autowired
    private PostFixtures postFixtures;

    @Autowired
    private DatabaseServiceInterface databaseService;

    public void init() {
        /** TRUNCATE ALL TABLES */
        databaseService.truncateAllTables();

        /** USERS */
        userFixtures.createMany(3).stream().forEach(userFixtures::store);
        userFixtures.storeAdmin();

        /** BLOG */
        tagFixtures.createMany(10).stream().forEach(tagFixtures::store);
        postFixtures.createMany(20).stream().forEach(postFixtures::store);
    }
}
