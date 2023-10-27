package com.donatus.fashion_blog_api.services;

public interface LikeServices {
    boolean likePost(Long postId);
    boolean likeComment(Long commentId);
    Long countPostLikes(Long postId);
    Long countCommentLikes(Long commentId);

}
