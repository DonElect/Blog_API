package com.donatus.fashion_blog_api.repository;

import com.donatus.fashion_blog_api.model.entity.Roles;
import com.donatus.fashion_blog_api.model.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RolesRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;


    @Test
    void existsByName() {
        Roles role1 = new Roles();
        role1.setName("SUPER_ADMIN");

        Roles role2 = new Roles();
        role2.setName("USER");

        UserEntity user1 = UserEntity.builder()
                .firstName("Paul")
                .lastName("Mike")
                .email("mike@gmail.com")
                .password("password")
                .confirmPassword("password")
                .userName("Miko")
                .location("Edo State")
                .roles(new ArrayList<>(List.of(role1)))
                .build();

        UserEntity user2 = UserEntity.builder()
                .firstName("Jane")
                .lastName("Mary")
                .email("mary@gmail.com")
                .password("password")
                .confirmPassword("password")
                .userName("Mary")
                .location("Lagos State")
                .roles(new ArrayList<>(List.of(role2)))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        boolean hasRole1 = rolesRepository.existsByName("SUPER_ADMIN");
        boolean hasRole2 = rolesRepository.existsByName("USER");

        Assertions.assertThat(hasRole1).isTrue();
        Assertions.assertThat(hasRole2).isTrue();
    }
}