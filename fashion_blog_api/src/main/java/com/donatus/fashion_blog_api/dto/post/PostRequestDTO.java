package com.donatus.fashion_blog_api.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {
    @Size(min = 2, max = 45)
    @NotBlank(message = "*required")
    private String postAuthor;

    @Size(min = 3, max = 25)
    @NotBlank(message = "*required")
    private String postCategory;

    @Size(min = 3, max = 45)
    @NotBlank(message = "*required")
    private String postTitle;

    @Size(min = 8, max = 1000)
    @NotBlank(message = "*required")
    private String postBody;

}
