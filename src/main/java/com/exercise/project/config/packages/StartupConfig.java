package com.exercise.project.config.packages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfig {

    @Value("${spring.application.name}")
    private String projectName;

    @Bean
    CommandLineRunner startupInfo() {
        return args -> {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("Project Running on : https://127.0.0.1:8000");
            System.out.println("=".repeat(70) + "\n");
        };
    }

}
