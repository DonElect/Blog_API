package com.donatus.blog_api.rest;

import com.donatus.blog_api.dto.AuthResponseDTO;
import com.donatus.blog_api.dto.LoginDTO;
import com.donatus.blog_api.dto.SignupDTO;
import com.donatus.blog_api.dto.user.UserResponseDTO;
import com.donatus.blog_api.services.LoginAndSignupServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class LoginAndSignupController {

    private final LoginAndSignupServices services;


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid SignupDTO signupDTO){
        return new ResponseEntity<>(services.registerUser(signupDTO), HttpStatus.CREATED);
    }

    @GetMapping("/verify_email/{token}")
    public ResponseEntity<String> VerifyEmail(@PathVariable String token){
        return new ResponseEntity<>(services.verifyEmail(token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> logIn(@RequestBody @Valid LoginDTO loginDTO){
        return new ResponseEntity<>(services.loginUser(loginDTO), HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verificationLinkRequest(){
        return ResponseEntity.ok(services.verificationRequest());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> viewUser(@PathVariable String userId){
        return ResponseEntity.ok(services.userDetails(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId){
        services.removeUser(userId);
        return ResponseEntity.status(202).body("User with ID: "+userId+" deleted");
    }
}