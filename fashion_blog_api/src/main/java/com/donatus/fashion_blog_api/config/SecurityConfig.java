package com.donatus.fashion_blog_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthEntryPoint authEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(configure ->
                configure
                        .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(antMatcher( "/javainuse-openapi/**")).permitAll()
                        // Signup and login authorization
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/users/login")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/users/signup")).permitAll()
                        // Post Authorizations
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/posts/**")).hasAnyAuthority("SUPER_ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/api/v1/posts/**")).hasAnyAuthority("SUPER_ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/posts/likes/**")).hasAnyAuthority("SUPER_ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/posts/**")).hasAnyAuthority("USER")
                        // Comment Authorizations
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/comments/**")).hasAnyAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/comments/**")).hasAnyAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/api/v1/comments/**")).hasAnyAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/api/v1/comments/**")).hasAnyAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/comments/likes/**")).hasAnyAuthority("USER")

                        .anyRequest().authenticated());

        http.httpBasic(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("ACTIVITY TRACKER API")
                        .description("Some custom description of API.")
                        .version("1.0").contact(new Contact().name("Donatus Okpala")
                                .email( "electroodob@gmail.com").url("https://www.linkedin.com/in/donatus-okpala-98a272199/"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
