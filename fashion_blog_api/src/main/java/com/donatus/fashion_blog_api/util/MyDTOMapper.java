package com.donatus.fashion_blog_api.util;

import com.donatus.fashion_blog_api.dto.ImageDataResponseDTO;
import com.donatus.fashion_blog_api.dto.comment.CommentResponseDTO;
import com.donatus.fashion_blog_api.dto.post.PostResponseDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.model.entity.CommentsEntity;
import com.donatus.fashion_blog_api.model.entity.ImageData;
import com.donatus.fashion_blog_api.model.entity.PostEntity;
import com.donatus.fashion_blog_api.model.entity.UserEntity;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class MyDTOMapper {

    public List<PostResponseDTO> mapPostResponse(List<PostEntity> post){
        return post.stream().map(p -> new PostResponseDTO(
                p.getPostId(),
                p.getPostAuthor(),
                p.getPostTitle(),
                p.getPostBody(),
                p.getPostDate(),
                p.getPostUpdate(),
                p.getCategory(),
                p.getImageData().stream().map(im -> ImageDataResponseDTO.builder()
                        .imageId(im.getImageId())
                        .postId(p.getPostId())
                        .imageUrl(im.getImageUrl())
                        .build()).toList()
        )).toList();
    }

    public List<UserResponseDTO> mapUserResponse(List<UserEntity> user){
        return user.stream().map(u -> new UserResponseDTO(
                u.getUserId(),
                u.getFirstName(),
                u.getLastName(),
                u.getEmail(),
                u.getUserName(),
                u.getLocation()
        )).toList();
    }

    public List<CommentResponseDTO> mapCommentResponse(List<CommentsEntity> comments){
        return comments.stream().map(c -> new CommentResponseDTO(
                c.getCommentId(),
                c.getComment(),
                c.getCommentDate(),
                c.getCommentUpdate()
        )).toList();
    }

    public ImageDataResponseDTO mapImageResponse(ImageData imageData){
        return ImageDataResponseDTO.builder()
                .imageId(imageData.getImageId())
                .imageUrl(imageData.getImageUrl())
                .postId(imageData.getPostEntity().getPostId())
                .build();
    }
}
