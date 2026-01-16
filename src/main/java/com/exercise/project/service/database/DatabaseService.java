package com.exercise.project.service.database;

public interface DatabaseService {

    /**
     * Make fixtures for all tables
     */
    public void performFixtures();

    /**
     * Truncate Users with his roles
     */
    public void truncateUsers();

    /**
     * Truncate All existings tables
     */
    public void truncateAllTables();

}
