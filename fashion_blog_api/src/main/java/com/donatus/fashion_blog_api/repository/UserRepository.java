package com.donatus.fashion_blog_api.repository;

import com.donatus.fashion_blog_api.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    Optional<UserEntity> findUserEntityByEmail(String email);

    @Query(value = "SELECT UserEntity FROM UserEntity \n" +
            "WHERE userId IN (SELECT userId FROM PostLikes WHERE PostEntity.postId = :postId)")
    Slice<UserEntity> findPostLiker(Long postId, Pageable pageable);

    @Query(value = "SELECT UserEntity FROM UserEntity \n" +
            "WHERE userId IN (SELECT userId FROM CommentLikes WHERE CommentsEntity.commentId = :commentId)")
    Slice<UserEntity> findCommentLiker(Long commentId, Pageable pageable);
}
