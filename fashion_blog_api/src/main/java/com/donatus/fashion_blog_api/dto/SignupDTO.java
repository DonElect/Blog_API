package com.donatus.fashion_blog_api.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupDTO {
    @NotBlank(message = "*required")
    @Size(min = 3, max = 25, message = "too long")
    private String firstName;

    @Size(max = 25, message = "too long")
    private String lastName;

    @Size(min = 8, max = 25)
    @NotBlank(message = "*required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "*required")
    @Size(min = 4, max = 15)
    private String password;

    @NotBlank(message = "*required")
    @Size(min = 4, max = 15)
    private String confirmPassword;

    @NotBlank(message = "*required")
    @Size(min = 3, max = 15)
    private String userName;

    @Size(max = 45, message = "too long")
    private String location;
}
