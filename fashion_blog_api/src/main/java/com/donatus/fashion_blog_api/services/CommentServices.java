package com.donatus.fashion_blog_api.services;

import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.dto.comment.CommentRequestDTO;
import com.donatus.fashion_blog_api.dto.comment.CommentResponseDTO;

import java.util.List;

public interface CommentServices {
    CommentResponseDTO makeComment(CommentRequestDTO comment);
    CommentResponseDTO viewComment(Long userId, Long commentId);
    CommentResponseDTO editComment(CommentRequestDTO comment);
    List<CommentResponseDTO> pageComment(Long pageNo, String sortBy);
    List<UserResponseDTO> pageCommentLiker(Long commentId);
    void deleteComment(Long commentId);
}
