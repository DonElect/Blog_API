package com.donatus.blog_api.repository;

import com.donatus.blog_api.model.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findByPostId(Long postId);
    Optional<PostEntity> findPostEntityByUserEntityUserIdAndPostId(String userId, Long postId);
}
