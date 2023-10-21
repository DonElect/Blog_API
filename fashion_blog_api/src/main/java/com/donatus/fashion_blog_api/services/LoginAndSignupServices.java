package com.donatus.fashion_blog_api.services;

import com.donatus.fashion_blog_api.dto.LoginDTO;
import com.donatus.fashion_blog_api.dto.SignupDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;

public interface LoginAndSignupServices {
    UserResponseDTO registerAdmin(SignupDTO admin);
    UserResponseDTO loginAdmin(LoginDTO admin);
    void removeAdmin(Long adminId);
}
