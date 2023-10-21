package com.donatus.fashion_blog_api.services;

import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.dto.post.PostRequestDTO;
import com.donatus.fashion_blog_api.dto.post.PostResponseDTO;

import java.util.List;

public interface PostServices {
    PostResponseDTO viewPost(Long adminId, Long postId);
    List<PostResponseDTO> pagePost(Long adminId, Integer pageNo, Integer pageSize);
    List<UserResponseDTO> pagePostLiker(Long postId, Integer pageNo, Integer pageSize);
    PostResponseDTO makePost(Long adminId, PostRequestDTO post);
    PostResponseDTO editPost(Long adminId, Long postId, PostRequestDTO post);
    void deletePost(Long postId);
}
