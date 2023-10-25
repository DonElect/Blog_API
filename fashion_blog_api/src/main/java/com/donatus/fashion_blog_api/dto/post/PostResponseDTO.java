package com.donatus.fashion_blog_api.dto.post;

import com.donatus.fashion_blog_api.dto.ImageDataResponseDTO;
import com.donatus.fashion_blog_api.model.entity.ImageData;
import com.donatus.fashion_blog_api.model.enums.PostCategory;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDTO {
    private Long postId;
    private String postAuthor;
    private String postTitle;
    private String postBody;
    private LocalDateTime postDate;
    private LocalDateTime postUpdate;
    private PostCategory category;
    private List<ImageDataResponseDTO> imageData;
}
