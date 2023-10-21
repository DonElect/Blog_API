package com.donatus.fashion_blog_api.services;

import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.dto.comment.CommentRequestDTO;
import com.donatus.fashion_blog_api.dto.comment.CommentResponseDTO;

import java.util.List;

public interface CommentServices {
    CommentResponseDTO makeComment(Long postId, Long userId, CommentRequestDTO comment);
    CommentResponseDTO viewComment(Long userId, Long commentId);
    CommentResponseDTO editComment(Long userId, Long commentId, CommentRequestDTO comment);
    List<CommentResponseDTO> pageComment(Long postId, Integer pageNo, Integer pageSize);
    List<UserResponseDTO> pageCommentLiker(Long commentId, Integer pageNo, Integer pageSize);
    void deleteComment(Long commentId);
}
