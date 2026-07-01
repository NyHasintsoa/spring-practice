package com.exercise.project.service.database;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class DatabaseServiceImpl implements DatabaseService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void performFixtures() {
    }

    @Override
    public void truncateUsers() {
        entityManager.createNativeQuery("TRUNCATE TABLE users, user_roles RESTART IDENTITY CASCADE").executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void truncateAllTables() {
        String query = """
            SELECT table_name 
            FROM information_schema.tables 
            WHERE table_schema = current_schema() 
              AND table_type = 'BASE TABLE'
              AND table_name NOT LIKE 'databasechangelog%' -- Optionnel : Éviter de vider Liquibase/Flyway si présent
        """;
        
        List<String> tableNames = entityManager.createNativeQuery(query).getResultList();
        
        if (!tableNames.isEmpty()) {
            String tables = String.join(", ", tableNames);
            entityManager.createNativeQuery("TRUNCATE TABLE " + tables + " RESTART IDENTITY CASCADE").executeUpdate();
        }
    }
}
