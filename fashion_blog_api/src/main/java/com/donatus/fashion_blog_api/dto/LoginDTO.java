package com.donatus.fashion_blog_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @Size(min = 8, max = 25)
    @NotBlank(message = "*required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "*required")
    @Size(min = 4, max = 15)
    private String password;
}
