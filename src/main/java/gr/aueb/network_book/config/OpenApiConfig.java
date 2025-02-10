package gr.aueb.network_book.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


        @OpenAPIDefinition(
                info = @Info(
                        contact = @Contact(
                                name = "bill",
                                email = "bill@example.com",
                                url = "https://example.com"
                        ),
                        description = "OpenApi documentation for Spring Security",
                        title = "OpenApi specification",
                        version = "1.0",
                        license = @License(
                                name = "Licence name",
                                url = "https://example-url.com"
                        ),
                        termsOfService = "Terms of service"
                ),
                servers = {
                        @Server(
                                description = "Local",
                                url = "http://localhost:8080/api/v1"
                        ),
                        @Server(
                                description = "Production",
                                url = "https://production.com"
                        )
                },
                security = {
                        @SecurityRequirement(
                                name = "bearerAuth"
                        )
                }
        )
        @SecurityScheme(
                name = "bearerAuth",
                description = "JWT auth description",
                scheme = "bearer",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                in = SecuritySchemeIn.HEADER

)
public class OpenApiConfig {
}
