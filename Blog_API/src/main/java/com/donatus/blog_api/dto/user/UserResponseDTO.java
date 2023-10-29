package com.donatus.blog_api.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String location;
}
