package com.donatus.fashion_blog_api.dto.comment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
    private Long commentId;
    private String comment;
    private LocalDateTime commentDate;
    private LocalDateTime commentUpdate;
}
