package com.exercise.project.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "API Documentation",
        version = "1.0",
        contact = @Contact(name = "Admin User", email = "admin@domain.com"),
        description = "Spring Boot Blog Application"
    ),
    servers = {
        @Server(
            description = "Local Server URL",
            url = "${project.backend.url}"
        )
    },
    security = {
        @SecurityRequirement(name = "bearer-jwt")
    }
)
@SecurityScheme(
    name = "bearer-jwt",
    description = "Paste token here to connect",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER,
    bearerFormat = "JWT"
)
public class OpenApiConfig {

}
