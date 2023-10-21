package com.donatus.fashion_blog_api.repository;

import com.donatus.fashion_blog_api.entity.CommentsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentsEntity, Long> {
    Optional<CommentsEntity> findCommentsEntityByUserEntityUserIdAndCommentId(Long userId, Long commentId);
    Slice<CommentsEntity> findCommentsEntityByPostEntityPostId(Long postId, Pageable pageable);
}
