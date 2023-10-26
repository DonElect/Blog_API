package com.donatus.fashion_blog_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        io.swagger.v3.oas.models.info.Contact contact = new Contact()
                .name("Donatus Okpala")
                .email("electroodob@gmail.com")
                .url("https://www.linkedin.com/in/donatus-okpala-98a272199/");

        License license = new License()
                .name("License of API")
                .url("API license URL");

        Info info = new Info()
                .title("Fashion Blog API")
                .description("A fashion blog api for fashion brands and influencers.")
                .version("1.0.0")
                .contact(contact)
                .license(license);

        Components components = new Components()
                .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme());

        SecurityRequirement securityRequirement = new SecurityRequirement().
                addList("Bearer Authentication");

        return new OpenAPI().addSecurityItem(securityRequirement)
                .components(components)
                .info(info);
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

}
