package com.exercise.project.fixtures;

import org.springframework.stereotype.Service;

import com.exercise.project.fixtures.auth.UserFixtures;
import com.exercise.project.fixtures.blog.PostFixtures;
import com.exercise.project.fixtures.blog.TagFixtures;
import com.exercise.project.service.database.DatabaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppFixtures {

    private final UserFixtures userFixtures;
    private final TagFixtures tagFixtures;
    private final PostFixtures postFixtures;
    private final DatabaseService databaseService;

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
