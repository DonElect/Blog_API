package com.donatus.fashion_blog_api.repository;

import com.donatus.fashion_blog_api.model.entity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    boolean existsByUserEntityUserIdAndCommentsEntityCommentId(Long userId, Long commentId);
    void deleteByUserEntityUserIdAndCommentsEntityCommentId(Long userId, Long comLikeId);
    Long countCommentLikesByCommentsEntityCommentId(Long commentId);
}
