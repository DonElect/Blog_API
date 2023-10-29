package com.donatus.blog_api.services;

import com.donatus.blog_api.dto.user.UserResponseDTO;
import com.donatus.blog_api.dto.comment.CommentRequestDTO;
import com.donatus.blog_api.dto.comment.CommentResponseDTO;

import java.util.List;

public interface CommentServices {
    CommentResponseDTO makeComment(Long postId, CommentRequestDTO comment);
    CommentResponseDTO viewComment(Long commentId);
    CommentResponseDTO editComment(Long commentId, CommentRequestDTO comment);
    List<CommentResponseDTO> pageComment(Long postId, Integer pageNo, Integer pageSize);
    List<UserResponseDTO> pageCommentLiker(Long commentId, Integer pageNo, Integer pageSize);
    void deleteComment(Long commentId);
}
