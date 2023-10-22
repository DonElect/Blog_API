package com.donatus.fashion_blog_api.dto.post;

import com.donatus.fashion_blog_api.dto.ImageDataResponseDTO;
import com.donatus.fashion_blog_api.model.entity.ImageData;
import com.donatus.fashion_blog_api.model.enums.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
