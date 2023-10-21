package com.donatus.fashion_blog_api.dto.post;

import com.donatus.fashion_blog_api.enums.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
}
