package com.donatus.blog_api.services;

import com.donatus.blog_api.dto.ImageDataResponseDTO;
import com.donatus.blog_api.dto.user.UserResponseDTO;
import com.donatus.blog_api.dto.post.PostRequestDTO;
import com.donatus.blog_api.dto.post.PostResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostServices {
    PostResponseDTO viewPost(Long postId);
    List<PostResponseDTO> pagePost(Integer pageNo, Integer pageSize);
    List<UserResponseDTO> pagePostLiker(Long postId, Integer pageNo, Integer pageSize);
    PostResponseDTO makePost(PostRequestDTO post);
    PostResponseDTO editPost(Long postId, PostRequestDTO post);
    ImageDataResponseDTO uploadPostImage(MultipartFile multipartFile, Long postId) throws IOException;
    void deletePost(Long postId);
    void deleteAPostImage(Long imageId);
}
