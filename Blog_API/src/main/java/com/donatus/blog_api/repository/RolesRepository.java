package com.donatus.blog_api.repository;

import com.donatus.blog_api.model.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    boolean existsByName(String name);
}
