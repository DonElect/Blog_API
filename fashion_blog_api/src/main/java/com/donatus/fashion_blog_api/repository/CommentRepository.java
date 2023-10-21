package com.donatus.fashion_blog_api.repository;

import com.donatus.fashion_blog_api.entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentsEntity, Long> {
}
