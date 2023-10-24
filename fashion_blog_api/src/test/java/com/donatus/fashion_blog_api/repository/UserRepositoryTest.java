package com.donatus.fashion_blog_api.repository;

import com.donatus.fashion_blog_api.model.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private UserEntity user1;
    UserEntity savedUser1;
    UserEntity savedUser2;

    @BeforeEach
    void setUp() {
        user1 = UserEntity.builder()
                .firstName("Paul")
                .lastName("Mike")
                .email("mike@gmail.com")
                .password("password")
                .confirmPassword("password")
                .userName("Miko")
                .location("Edo State")
                .build();

        UserEntity user2 = UserEntity.builder()
                .firstName("Jane")
                .lastName("Mary")
                .email("mary@gmail.com")
                .password("password")
                .confirmPassword("password")
                .userName("Mary")
                .location("Lagos State")
                .build();

        savedUser1 = userRepository.save(user1);
        savedUser2 = userRepository.save(user2);
    }

    @Test
    void saveNewUser(){
        UserEntity savedUser = userRepository.save(user1);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUserId()).isGreaterThan(0);
    }

    @Test
    void deleteUserById(){

        userRepository.deleteById(savedUser1.getUserId());

        Optional<UserEntity> deletedUser = userRepository.findById(savedUser1.getUserId());

        Assertions.assertThat(deletedUser.isEmpty()).isTrue();
    }

    @Test
    void existsByEmail() {
        Assertions.assertThat(userRepository.existsByEmail(user1.getEmail())).isTrue();
    }

    @Test
    void findUserEntityByEmail() {
        Optional<UserEntity> foundUser = userRepository.findUserEntityByEmail(user1.getEmail());

        Assertions.assertThat(foundUser.isPresent()).isTrue();
    }
}