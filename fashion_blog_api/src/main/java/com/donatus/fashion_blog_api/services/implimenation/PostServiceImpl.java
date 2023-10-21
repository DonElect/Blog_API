package com.donatus.fashion_blog_api.services.implimenation;

import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.dto.post.PostRequestDTO;
import com.donatus.fashion_blog_api.dto.post.PostResponseDTO;
import com.donatus.fashion_blog_api.entity.PostEntity;
import com.donatus.fashion_blog_api.entity.UserEntity;
import com.donatus.fashion_blog_api.exception.PostNotFoundException;
import com.donatus.fashion_blog_api.exception.UserNotFoundException;
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

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostServices {

    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapper = new ModelMapper();
    private final MyDTOMapper myDTOMapper = new MyDTOMapper();


    @Override
    public PostResponseDTO viewPost(Long userId, Long postId) {
        PostEntity post = postRepo.findPostEntityByUserEntityUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new PostNotFoundException("No post found!"));

        return mapper.map(post, PostResponseDTO.class);
    }

    @Override
    public List<PostResponseDTO> pagePost(Long adminId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdDate"));

        Slice<PostEntity> result = postRepo.findPostEntitiesByUserEntityUserId(adminId, pageable);

        return result.isEmpty() ? new ArrayList<>() : myDTOMapper.mapPostResponse(result.getContent());
    }

    @Override
    public List<UserResponseDTO> pagePostLiker(Long postId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("firstName"));

        Slice<UserEntity> result = userRepo.findPostLiker(postId, pageable);

        return result.isEmpty() ? new ArrayList<>() :myDTOMapper.mapUserResponse(result.getContent());
    }

    @Override
    public PostResponseDTO makePost(Long adminId, PostRequestDTO post) {
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

        // TODO: Check is this code works
        mapper.map(post, oldPost);

        admin.addPost(oldPost);

        PostEntity newPost = postRepo.save(oldPost);

        return mapper.map(newPost, PostResponseDTO.class);
    }

    @Override
    public void deletePost(Long postId) {
        postRepo.deleteById(postId);
    }
}
