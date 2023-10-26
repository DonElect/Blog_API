package com.donatus.fashion_blog_api.services.implimenation;

import com.donatus.fashion_blog_api.dto.AuthResponseDTO;
import com.donatus.fashion_blog_api.dto.LoginDTO;
import com.donatus.fashion_blog_api.dto.SignupDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.model.entity.Roles;
import com.donatus.fashion_blog_api.model.entity.UserEntity;
import com.donatus.fashion_blog_api.repository.RolesRepository;
import com.donatus.fashion_blog_api.repository.UserRepository;
import com.donatus.fashion_blog_api.security.JWTGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginAndSignupServiceImplTest {
    @InjectMocks
    private LoginAndSignupServiceImpl service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTGenerator jwtGenerator;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);
    private UserEntity user1;
    private SignupDTO signupDTO;
    private LoginDTO loginDTO;



    @BeforeEach
    void setUp() {
        Roles role = new Roles();
        role.setName("USER");

        user1 = UserEntity.builder()
                .firstName("Paul")
                .lastName("Mike")
                .email("mike@gmail.com")
                .password("password")
                .confirmPassword("password")
                .userName("Miko")
                .location("Edo State")
                .roles(new HashSet<>(List.of(role)))
                .build();


        signupDTO = SignupDTO.builder()
                .firstName(user1.getFirstName())
                .lastName(user1.getLastName())
                .password(user1.getPassword())
                .confirmPassword(user1.getConfirmPassword())
                .email(user1.getEmail())
                .location(user1.getLocation())
                .build();

        loginDTO = LoginDTO.builder()
                .email(user1.getEmail())
                .password(user1.getPassword())
                .build();
    }


    @Test
    void registerUser() {
        when(rolesRepository.existsByName(Mockito.anyString())).thenReturn(true);
        when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(user1);

        UserResponseDTO savedUser = service.registerUser(signupDTO);

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    void loginUser() {
        // Encrypt password
        String hashedPass = encoder.encode(user1.getPassword());
        user1.setPassword(hashedPass);

        when(userRepository.findUserEntityByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(user1));
        when(jwtGenerator.generateToken(Mockito.any())).thenReturn(Mockito.anyString());

        AuthResponseDTO authResponseDTO = service.loginUser(loginDTO);

        Assertions.assertThat(authResponseDTO).isNotNull();
    }

    @Test
    void removeUser() {
        org.junit.jupiter.api.Assertions.assertAll(() -> service.removeUser(1L));
    }

    @Test
    void userDetails() {
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(user1));

        UserResponseDTO foundUser = service.userDetails(1L);

        Assertions.assertThat(foundUser).isNotNull();
    }
}