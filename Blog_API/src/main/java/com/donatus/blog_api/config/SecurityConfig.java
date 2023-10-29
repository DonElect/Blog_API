package com.donatus.blog_api.config;

import com.donatus.blog_api.security.JWTAuthenticationFilter;
import com.donatus.blog_api.security.JwtAuthEntryPoint;
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
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/users/verify_email/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/users/verify")).hasAnyAuthority("USER")
                        // Post Authorizations
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/posts/**")).hasAnyAuthority("SUPER_ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/posts/images/**")).hasAnyAuthority("SUPER_ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/api/v1/posts/images/**")).hasAnyAuthority("SUPER_ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/api/v1/posts/**")).hasAnyAuthority("SUPER_ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/posts/likes/**")).hasAnyAuthority("SUPER_ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/api/v1/posts/**")).hasAnyAuthority("SUPER_ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/posts/**")).hasAnyAuthority("USER")
                        // Comment Authorizations
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/comments/**")).hasAnyAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/comments/**")).hasAnyAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/api/v1/comments/**")).hasAnyAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/api/v1/comments/**")).hasAnyAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/comments/likes/**")).hasAnyAuthority("USER")
                        // Likes Authorisation
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/likes/posts/**")).hasAnyAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/likes/comments/**")).hasAnyAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/likes/posts/count/**")).hasAnyAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/likes/comments/count/**")).hasAnyAuthority("USER")

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

}
