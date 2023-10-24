package com.donatus.fashion_blog_api.services.implimenation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.donatus.fashion_blog_api.dto.post.PostRequestDTO;
import com.donatus.fashion_blog_api.dto.post.PostResponseDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.model.entity.PostEntity;
import com.donatus.fashion_blog_api.model.entity.Roles;
import com.donatus.fashion_blog_api.model.entity.UserEntity;
import com.donatus.fashion_blog_api.repository.ImageDataRepository;
import com.donatus.fashion_blog_api.repository.PostRepository;
import com.donatus.fashion_blog_api.repository.RolesRepository;
import com.donatus.fashion_blog_api.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl service;
    @InjectMocks
    private Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dfjzskbe6",
            "api_key", "473221831389556",
            "api_secret", "Yy8o03dCGqc219TUQCV9OQJl1Wc"));
    @Mock
    private UserRepository userRepository;
    @Mock
    private RolesRepository rolesRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private ImageDataRepository imageRepo;

    private UserEntity user1;
    private PostEntity postEntity;
    private PostResponseDTO postResponseDTO;
    private PostRequestDTO postRequestDTO;

    @BeforeEach
    void setUp(){
        Roles role = new Roles();
        role.setName("USER");

        user1 = UserEntity.builder()
                .firstName("Paul")
                .lastName("Mike")
                .email("mike@gmail.com")
                .password("password")
                .confirmPassword("password")
                .userName("Miko")
                .location("Edo State")
                .roles(new ArrayList<>(List.of(role)))
                .build();

        postRequestDTO = PostRequestDTO.builder()
                .postAuthor("Donatus")
                .postCategory("SHOES")
                .postTitle("Testing")
                .postBody("How to test a post service")
                .build();
    }

    @Test
    void makePost() {
        PostEntity post = Mockito.mock(PostEntity.class);
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(user1));
        when(postRepository.save(Mockito.any(PostEntity.class))).thenReturn(post);

        PostResponseDTO savedPost = service.makePost(1L, postRequestDTO);

        Assertions.assertThat(savedPost).isNotNull();
    }

    @Test
    void editPost() {
        PostEntity post = Mockito.mock(PostEntity.class);
        PostEntity oldPost = Mockito.mock(PostEntity.class);
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(user1));
        when(postRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(oldPost));
        when(postRepository.save(Mockito.any(PostEntity.class))).thenReturn(post);

        PostResponseDTO updatedPost = service.editPost(1L, 2L, postRequestDTO);

        Assertions.assertThat(updatedPost).isNotNull();
    }
    @Test
    void viewPost() {
        PostEntity post = Mockito.mock(PostEntity.class);
        when(postRepository.findPostEntityByUserEntityUserIdAndPostId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(post));

        PostResponseDTO foundPost = service.viewPost(1L, 1L);

        Assertions.assertThat(foundPost).isNotNull();
    }

    @Test
    void pagePost() {
        PostEntity post = Mockito.mock(PostEntity.class);
        Slice<PostEntity> postEntities =  new SliceImpl<>(Collections.singletonList(post));

        when(postRepository.findByUserEntityUserId(Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(postEntities);

        List<PostResponseDTO> result = service.pagePost(1L, 0, 2);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(!result.isEmpty()).isTrue();
    }

    @Test
    void pagePostLiker() {
        UserEntity user = Mockito.mock(UserEntity.class);
        Slice<UserEntity> userEntities = new SliceImpl<>(Collections.singletonList(user));

        when(userRepository.findPostLiker(Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(userEntities);

        List<UserResponseDTO> responseDTOS = service.pagePostLiker(1L, 0, 3);

        Assertions.assertThat(responseDTOS).isNotNull();
        Assertions.assertThat(!responseDTOS.isEmpty()).isTrue();
    }

    @Test
    void uploadPostImage() throws IOException {
//        this.cloudinary = notNull();
//        MultipartFile file = new MockMultipartFile("Mockito.anyString()", new byte[]{(byte) 1000});
//        ImageData imageData = Mockito.mock(ImageData.class);
//
//        PostEntity post = Mockito.mock(PostEntity.class);
//
//        String imageUrl = "the url";
//
//        when(cloudinary.uploader().upload(Mockito.any(), Mockito.any()).get(Mockito.anyString()).toString()).thenReturn(imageUrl);
////        when(this.cloudinary).thenReturn(notNull());
////        when(service.getImageUrl(Mockito.any())).thenReturn(imageUrl);
//        when(postRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(post));
//        when(imageRepo.save(Mockito.any(ImageData.class))).thenReturn(imageData);
//
//        ImageDataResponseDTO imageDataResponseDTO = service.uploadPostImage(file, 1L);
//
//        Assertions.assertThat(imageDataResponseDTO).isNotNull();
//        IntStream.
    }

    @Test
    void deletePost() {
        org.junit.jupiter.api.Assertions.assertAll(() -> service.deletePost(1L));
    }

    @Test
    void deleteAPostImage() {
        org.junit.jupiter.api.Assertions.assertAll(() -> service.deleteAPostImage(1L));
    }
}