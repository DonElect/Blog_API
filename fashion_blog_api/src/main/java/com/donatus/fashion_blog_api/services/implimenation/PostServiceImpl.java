package com.donatus.fashion_blog_api.services.implimenation;

import com.cloudinary.Cloudinary;
import com.donatus.fashion_blog_api.dto.ImageDataResponseDTO;
import com.donatus.fashion_blog_api.dto.post.PostRequestDTO;
import com.donatus.fashion_blog_api.dto.post.PostResponseDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.exception.PostNotFoundException;
import com.donatus.fashion_blog_api.exception.UserNotFoundException;
import com.donatus.fashion_blog_api.model.entity.ImageData;
import com.donatus.fashion_blog_api.model.entity.PostEntity;
import com.donatus.fashion_blog_api.model.entity.UserEntity;
import com.donatus.fashion_blog_api.repository.ImageDataRepository;
import com.donatus.fashion_blog_api.repository.PostRepository;
import com.donatus.fashion_blog_api.repository.UserRepository;
import com.donatus.fashion_blog_api.services.PostServices;
import com.donatus.fashion_blog_api.util.MyDTOMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostServices {

    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final ImageDataRepository imageRepo;
    private final ModelMapper mapper = new ModelMapper();
    private final MyDTOMapper myDTOMapper = new MyDTOMapper();
    private final Cloudinary cloudinary;


    @Override
    public PostResponseDTO viewPost(Long userId, Long postId) {
        PostEntity post = postRepo.findPostEntityByUserEntityUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new PostNotFoundException("No post found!"));

        return mapper.map(post, PostResponseDTO.class);
    }

    @Override
    public List<PostResponseDTO> pagePost(Long adminId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("postDate"));
        Slice<PostEntity> result = postRepo.findByUserEntityUserId(adminId, pageable);
        return result.isEmpty() ? new ArrayList<>() : myDTOMapper.mapPostResponse(result.getContent());
    }

    @Override
    public List<UserResponseDTO> pagePostLiker(Long postId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("first_name"));

        Slice<UserEntity> result = userRepo.findPostLiker(postId, pageable);

        return result.isEmpty() ? new ArrayList<>() :myDTOMapper.mapUserResponse(result.getContent());
    }

    @Override
    public PostResponseDTO makePost(Long adminId, PostRequestDTO post)  {
        UserEntity admin = userRepo.findById(adminId)
                .orElseThrow(() -> new UserNotFoundException("No admin with id: "+ adminId+" found."));

        PostEntity newPost = mapper.map(post, PostEntity.class);

        admin.addPost(newPost);
        PostEntity saved = postRepo.save(newPost);
        return mapper.map(saved, PostResponseDTO.class);
    }

    @Override
    public PostResponseDTO editPost(Long adminId, Long postId, PostRequestDTO post) {
        UserEntity admin = userRepo.findById(adminId)
                .orElseThrow(() -> new UserNotFoundException("No admin with id: "+ adminId+" found."));

        PostEntity oldPost = postRepo.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post with id: "+postId+" not found."));

        mapper.map(post, oldPost);

        admin.addPost(oldPost);

        PostEntity newPost = postRepo.save(oldPost);

        return mapper.map(newPost, PostResponseDTO.class);
    }

    @Override
    public ImageDataResponseDTO uploadPostImage(MultipartFile multipartFile, Long postId) throws IOException {
        String imageUrl =  cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();

        ImageData newImage = ImageData.builder()
                .imageUrl(imageUrl)
                .build();

        PostEntity post = postRepo
                .findById(postId).orElseThrow(() -> new PostNotFoundException("Post with id: "+postId+" not found"));

        post.addPostImage(newImage);
        ImageData savedImage = imageRepo.save(newImage);
        return myDTOMapper.mapImageResponse(savedImage);
    }

    @Override
    public void deletePost(Long postId) {
        postRepo.deleteById(postId);
    }

    @Override
    public void deleteAPostImage(Long imageId) {
        imageRepo.deleteById(imageId);
    }
}
