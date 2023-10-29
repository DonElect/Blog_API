package com.donatus.blog_api.services.implimenation;

import com.donatus.blog_api.model.entity.*;
import com.donatus.blog_api.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {

    @InjectMocks
    private LikeServiceImpl likeService;

    @Mock
    private CommentLikesRepository commentLikesRepo;
    @Mock
    private PostLikesRepository postLikesRepo;

    @Mock
    private UserRepository userRepo;
    @Mock
    private PostRepository postRepo;
    @Mock
    private CommentRepository commentRepo;
    @Mock
    private Authentication authentication;
    private PostLikes postLikes;
    private CommentLikes commentLikes;



    @BeforeEach
    void setUp() {
        postLikes = PostLikes.builder()
                .post_like_id(1L)
                .build();
        commentLikes = CommentLikes.builder()
                .comLikeId(1L)
                .build();

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void likePost() {
        UserEntity user = Mockito.mock(UserEntity.class);
        PostEntity post = Mockito.mock(PostEntity.class);

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("email@gmail.com");
        when(userRepo.findUserEntityByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(postRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(post));
        when(postLikesRepo.save(Mockito.any(PostLikes.class))).thenReturn(postLikes);

        boolean isLiked = likeService.likePost( 1L);

        Assertions.assertThat(isLiked).isTrue();
    }

    @Test
    void likeComment() {
        UserEntity user = Mockito.mock(UserEntity.class);
        CommentsEntity comment = Mockito.mock(CommentsEntity.class);

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("email@gmail.com");
        when(userRepo.findUserEntityByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(commentRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(comment));
        when(commentLikesRepo.save(Mockito.any(CommentLikes.class))).thenReturn(commentLikes);

        boolean isLiked = likeService.likeComment( 1L);

        Assertions.assertThat(isLiked).isTrue();
    }

    @Test
    void countPostLikes() {
        Long value = 3L;

        when(postLikesRepo.countPostLikesByPostEntityPostId(Mockito.anyLong())).thenReturn(value);

        Long count = likeService.countPostLikes(1L);

        Assertions.assertThat(count).isGreaterThan(0);
    }

    @Test
    void countCommentLikes() {
        Long value = 3L;

        when(commentLikesRepo.countCommentLikesByCommentsEntityCommentId(Mockito.anyLong())).thenReturn(value);

        Long count = likeService.countCommentLikes(1L);

        Assertions.assertThat(count).isGreaterThan(0);
    }
}