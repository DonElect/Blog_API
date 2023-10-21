package com.donatus.fashion_blog_api.repository;

import com.donatus.fashion_blog_api.model.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    boolean existsByUserEntityUserIdAndPostEntityPostId(Long userId, Long postId);
    void deleteByUserEntityUserIdAndPostEntityPostId(Long userId, Long postEntity_postId);
    Long countPostLikesByPostEntityPostId(Long postId);
}
