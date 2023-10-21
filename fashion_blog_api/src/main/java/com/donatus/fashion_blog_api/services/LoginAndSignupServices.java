package com.donatus.fashion_blog_api.services;

import com.donatus.fashion_blog_api.dto.AuthResponseDTO;
import com.donatus.fashion_blog_api.dto.LoginDTO;
import com.donatus.fashion_blog_api.dto.SignupDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;

public interface LoginAndSignupServices {
    UserResponseDTO registerUser(SignupDTO admin);
    AuthResponseDTO loginUser(LoginDTO admin);
    void removeUser(Long adminId);
    UserResponseDTO userDetails(Long userId);
}
