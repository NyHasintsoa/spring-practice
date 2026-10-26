package com.exercise.project.fixture;

import org.springframework.stereotype.Service;

import com.exercise.project.fixture.auth.UserFixture;
import com.exercise.project.fixture.blog.PostFixture;
import com.exercise.project.fixture.blog.TagFixture;
import com.exercise.project.service.database.DatabaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppFixture {

    private final UserFixture userFixtures;
    private final TagFixture tagFixtures;
    private final PostFixture postFixtures;
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
