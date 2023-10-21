package com.donatus.fashion_blog_api.repository;

import com.donatus.fashion_blog_api.model.entity.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findPostEntityByUserEntityUserIdAndPostId(Long userId, Long postId);
    Slice<PostEntity> findPostEntityByUserEntityUserId(Long userId, Pageable pageable);
    Slice<PostEntity> findByUserEntityUserId(Long userId, Pageable pageable);

}
