package com.exercise.project.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfig {

    @Value("${spring.application.name}")
    private String projectName;

    @Value("${server.ssl.enabled}")
    private Boolean sslEnabled;

    @Bean
    CommandLineRunner startupInfo() {
        return args -> {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("Project Running on : "+ (sslEnabled ? "https" : "http") +"://127.0.0.1:8000");
            System.out.println("=".repeat(70) + "\n");
        };
    }

}
