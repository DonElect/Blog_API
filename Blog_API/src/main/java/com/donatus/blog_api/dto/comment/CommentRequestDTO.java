package com.donatus.blog_api.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDTO {

    @NotBlank(message = "*required")
    @Size(min = 1, max = 1000)
    private String comment;
}
