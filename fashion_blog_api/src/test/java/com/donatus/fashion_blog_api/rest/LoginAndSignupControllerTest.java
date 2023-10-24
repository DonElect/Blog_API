package com.donatus.fashion_blog_api.rest;

import com.donatus.fashion_blog_api.dto.AuthResponseDTO;
import com.donatus.fashion_blog_api.dto.LoginDTO;
import com.donatus.fashion_blog_api.dto.SignupDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.services.LoginAndSignupServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = LoginAndSignupController.class)
@AutoConfigureMockMvc(addFilters = false)  // Disables spring security
@ExtendWith(MockitoExtension.class)
class LoginAndSignupControllerTest {

    @Autowired
    private LoginAndSignupController controller;

    @MockBean
    private LoginAndSignupServices loginAndSignupServices;

    @Autowired
    private ObjectMapper objectMapper;

    private UserResponseDTO responseDTO;
    private SignupDTO signupDTO;
    private LoginDTO loginDTO;

    @BeforeEach
    void setUp() {

        responseDTO = UserResponseDTO.builder()
                .userId(1L)
                .firstName("Jane")
                .lastName("Mary")
                .email("mary@gmail.com")
                .userName("Mary")
                .location("Lagos State")
                .build();

        signupDTO = SignupDTO.builder()
                .firstName("Donatus")
                .lastName("Donatus")
                .email("dona@gmail.com")
                .password("1234")
                .confirmPassword("1234")
                .userName("Dona")
                .location("Edo State")
                .build();

        loginDTO = LoginDTO.builder()
                .email("dona@gmail.com")
                .password("1234")
                .build();

    }

    @Test
    void signUp() throws Exception{
        when(loginAndSignupServices.registerUser(Mockito.any(SignupDTO.class))).thenReturn(responseDTO);

        String content = objectMapper.writeValueAsString(signupDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"userId\":1,\"firstName\":\"Jane\",\"lastName\":\"Mary\",\"email\":\"mary@gmail.com\",\"userName\":\"Mary\",\"location\":\"Lagos State\"}"));
    }

    @Test
    void logIn() throws Exception {
        AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                .accessToken("1234567890123456789012345678901234567890")
                .build();
        when(loginAndSignupServices.loginUser(Mockito.any(LoginDTO.class))).thenReturn(authResponseDTO);

        String content = objectMapper.writeValueAsString(loginDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"accessToken\":\"1234567890123456789012345678901234567890\"}"));
    }

    @Test
    void viewUser() throws Exception {

        when(loginAndSignupServices.userDetails(Mockito.anyLong())).thenReturn(responseDTO);

        String content = objectMapper.writeValueAsString(loginDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/users/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"userId\":1,\"firstName\":\"Jane\",\"lastName\":\"Mary\",\"email\":\"mary@gmail.com\",\"userName\":\"Mary\",\"location\":\"Lagos State\"}"));
    }

    @Test
    void deleteUser() throws Exception {
        doNothing().when(loginAndSignupServices).removeUser(Mockito.anyLong());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/users/{userId}",
                1L);
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}