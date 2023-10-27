package com.donatus.fashion_blog_api.services.implimenation;

import com.donatus.fashion_blog_api.dto.comment.CommentRequestDTO;
import com.donatus.fashion_blog_api.dto.comment.CommentResponseDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.model.entity.CommentsEntity;
import com.donatus.fashion_blog_api.model.entity.PostEntity;
import com.donatus.fashion_blog_api.model.entity.UserEntity;
import com.donatus.fashion_blog_api.repository.CommentRepository;
import com.donatus.fashion_blog_api.repository.PostRepository;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private UserRepository userRepo;
    @Mock
    private PostRepository postRepo;
    @Mock
    private CommentRepository commentRepo;
    @Mock
    private Authentication authentication;
    private CommentRequestDTO comment;


    @BeforeEach
    void setUp(){
        comment = CommentRequestDTO.builder()
                .comment("How far my guy!")
                .build();

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void makeComment() {
        UserEntity user = Mockito.mock(UserEntity.class);
        PostEntity post = Mockito.mock(PostEntity.class);
        CommentsEntity newComment = Mockito.mock(CommentsEntity.class);

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("email@gmail.com");
        when(postRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(post));
        when(userRepo.findUserEntityByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(commentRepo.save(Mockito.any(CommentsEntity.class))).thenReturn(newComment);

        CommentResponseDTO commentResponseDTO = commentService.makeComment(1L,  comment);

        Assertions.assertThat(commentResponseDTO).isNotNull();
    }

    @Test
    void viewComment() {
        CommentsEntity newComment = Mockito.mock(CommentsEntity.class);

        when(commentRepo.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(newComment));

        CommentResponseDTO commentResponseDTO = commentService.viewComment( 1L);

        Assertions.assertThat(commentResponseDTO).isNotNull();
    }

    @Test
    void editComment() {
        UserEntity user = Mockito.mock(UserEntity.class);
        CommentsEntity newComment = Mockito.mock(CommentsEntity.class);
        CommentsEntity oldComment = Mockito.mock(CommentsEntity.class);

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("email@gmail.com");
        when(userRepo.findUserEntityByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(commentRepo.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(oldComment));
        when(commentRepo.save(Mockito.any(CommentsEntity.class))).thenReturn(newComment);

        CommentResponseDTO commentResponseDTO = commentService.editComment( 1L, comment);

        Assertions.assertThat(commentResponseDTO).isNotNull();
    }

    @Test
    void pageComment() {
        CommentsEntity comment = Mockito.mock(CommentsEntity.class);

        Slice<CommentsEntity> result = new SliceImpl<>(Collections.singletonList(comment));
        when(commentRepo.findCommentsEntityByPostEntityPostId(Mockito.anyLong(), Mockito.any(Pageable.class)))
                .thenReturn(result);

        List<CommentResponseDTO> foundComments = commentService.pageComment(1L, 0, 3);

        Assertions.assertThat(foundComments).isNotNull();
        Assertions.assertThat(!foundComments.isEmpty()).isTrue();
    }

    @Test
    void pageCommentLiker() {
        UserEntity user = Mockito.mock(UserEntity.class);
        Slice<UserEntity> result = new SliceImpl<>(Collections.singletonList(user));

        when(userRepo.findCommentLiker(Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(result);

        List<UserResponseDTO> foundUsers = commentService.pageCommentLiker(1L, 0, 2);

        Assertions.assertThat(foundUsers).isNotNull();
        Assertions.assertThat(!foundUsers.isEmpty()).isTrue();
    }

    @Test
    void deleteComment() {
        org.junit.jupiter.api.Assertions.assertAll(() -> commentService.deleteComment(1L));
    }
}