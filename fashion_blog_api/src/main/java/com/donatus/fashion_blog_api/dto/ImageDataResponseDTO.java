package com.donatus.fashion_blog_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDataResponseDTO {
    private Long imageId;
    private Long postId;
    private String imageUrl;
}
