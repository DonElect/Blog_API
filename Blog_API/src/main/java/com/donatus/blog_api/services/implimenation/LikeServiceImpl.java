package com.donatus.blog_api.services.implimenation;

import com.donatus.blog_api.exception.CommentNotFoundException;
import com.donatus.blog_api.exception.PostNotFoundException;
import com.donatus.blog_api.exception.UserNotFoundException;
import com.donatus.blog_api.model.entity.*;
import com.donatus.blog_api.repository.*;
import com.donatus.blog_api.services.LikeServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeServices {

    private final CommentLikesRepository commentLikesRepo;
    private final PostLikesRepository postLikesRepo;
    private final UserRepository userRepo;
    private final CommentRepository commentRepo;
    private final PostRepository postRepo;


    @Transactional
    @Override
    public boolean likePost(Long postId) {
        String email =  SecurityContextHolder.getContext().getAuthentication().getName();

        // Post unliked
        if (postLikesRepo.deleteByUserEntityEmailAndPostEntityPostId(email, postId) > 0){
            return false;
        }

        PostLikes like = new PostLikes();
        UserEntity user = userRepo.findUserEntityByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: "+email+" not found!"));

        PostEntity post = postRepo.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post with id: "+postId+" not found!"));

        user.addPostLike(like);
        post.addPostLike(like);

        PostLikes savedLike = postLikesRepo.save(like);

        return savedLike.getPost_like_id() > 0;
    }

    @Transactional
    @Override
    public boolean likeComment(Long commentId) {
        String email =  SecurityContextHolder.getContext().getAuthentication().getName();

        // Comment unliked
        if (commentLikesRepo.deleteByUserEntityEmailAndCommentsEntityCommentId(email, commentId) > 0){
            return false;
        }

        CommentLikes like = new CommentLikes();
        UserEntity user = userRepo.findUserEntityByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: "+email+" not found!"));

        CommentsEntity comments = commentRepo.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Post with id: "+commentId+" not found!"));

        user.addCommentLike(like);
        comments.addCommentLikes(like);

        CommentLikes savedLike = commentLikesRepo.save(like);

        return savedLike.getComLikeId() > 0;
    }

    @Override
    public Long countPostLikes(Long postId) {
        return postLikesRepo.countPostLikesByPostEntityPostId(postId);
    }

    @Override
    public Long countCommentLikes(Long commentId) {
        return commentLikesRepo.countCommentLikesByCommentsEntityCommentId(commentId);
    }
}
