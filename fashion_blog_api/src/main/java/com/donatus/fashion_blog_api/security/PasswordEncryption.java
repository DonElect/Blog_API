package com.donatus.fashion_blog_api.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryption {

    public String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean isValid(String plainPassword, String hashedPassword){
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
