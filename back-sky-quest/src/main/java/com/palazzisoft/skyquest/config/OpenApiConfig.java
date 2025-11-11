package com.palazzisoft.skyquest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger configuration for SkyQuest backend.
 *
 * This uses springdoc-openapi to automatically generate the OpenAPI 3 specification
 * for all REST endpoints.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SkyQuest API",
                version = "v1",
                description = "REST API for SkyQuest — astronomical gear and catalog services.",
                contact = @Contact(name = "SkyQuest", email = "support@palazzisoft.com"),
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
        ),
        servers = {
                @Server(url = "/", description = "Default Server")
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
    // Additional customization can be added here if needed later.
}
