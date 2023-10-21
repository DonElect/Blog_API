package com.donatus.fashion_blog_api.rest;

import com.donatus.fashion_blog_api.config.JWTGenerator;
import com.donatus.fashion_blog_api.dto.AuthResponseDTO;
import com.donatus.fashion_blog_api.dto.LoginDTO;
import com.donatus.fashion_blog_api.dto.SignupDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.services.LoginAndSignupServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class LoginAndSignupController {

    private final LoginAndSignupServices services;


    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signUp(@RequestBody SignupDTO signupDTO){
        return ResponseEntity.ok(services.registerUser(signupDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> logIn(@RequestBody LoginDTO loginDTO){
        return new  ResponseEntity<>(services.loginUser(loginDTO), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> viewUser(@PathVariable Long userId){
        return ResponseEntity.ok(services.userDetails(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        services.removeUser(userId);
        return ResponseEntity.ok("User with ID: "+userId+" deleted");
    }
}
