package com.donatus.fashion_blog_api.services;

public interface LikeServices {
    // Check if post is already liked by the user and unlike if true
    boolean likePost(Long userId, Long postId);
    boolean likeComment(Long userId, Long commentId);
    Long countPostLikes(Long postId);
    Long countCommentLikes(Long commentId);

}
