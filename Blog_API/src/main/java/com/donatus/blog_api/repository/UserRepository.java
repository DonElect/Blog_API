package com.donatus.blog_api.repository;

import com.donatus.blog_api.model.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByEmail(String email);
    Optional<UserEntity> findUserEntityByEmail(String email);

    @Query(nativeQuery = true ,value = "SELECT * FROM blog_users \n" +
            "WHERE user_id IN (SELECT user_id FROM post_likes WHERE post_id = :postId)")
    Slice<UserEntity> findPostLiker(Long postId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM blog_users \n" +
            "WHERE user_id IN (SELECT user_id FROM comment_likes WHERE comment_id = :commentId)")
    Slice<UserEntity> findCommentLiker(Long commentId, Pageable pageable);
}
