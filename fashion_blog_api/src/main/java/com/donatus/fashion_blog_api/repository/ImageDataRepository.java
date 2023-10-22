package com.donatus.fashion_blog_api.repository;

import com.donatus.fashion_blog_api.model.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
}
