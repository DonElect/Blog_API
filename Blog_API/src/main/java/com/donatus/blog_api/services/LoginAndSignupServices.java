package com.donatus.blog_api.services;

import com.donatus.blog_api.dto.AuthResponseDTO;
import com.donatus.blog_api.dto.LoginDTO;
import com.donatus.blog_api.dto.SignupDTO;
import com.donatus.blog_api.dto.user.UserResponseDTO;

public interface LoginAndSignupServices {
    String registerUser(SignupDTO admin);
    String verifyEmail(String token);
    AuthResponseDTO loginUser(LoginDTO admin);
    void removeUser(String adminId);
    UserResponseDTO userDetails(String userId);
    String verificationRequest();
}
