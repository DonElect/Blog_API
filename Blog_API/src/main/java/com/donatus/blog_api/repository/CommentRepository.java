package com.donatus.blog_api.repository;

import com.donatus.blog_api.model.entity.CommentsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentsEntity, Long> {
    Slice<CommentsEntity> findCommentsEntityByPostEntityPostId(Long postId, Pageable pageable);
}
