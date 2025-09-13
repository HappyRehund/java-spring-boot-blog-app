package com.rehund.blog.repository;

import com.rehund.blog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findByIsDeleted(boolean isDeleted, Pageable pageable);
    Optional<Category> findByIdAndIsDeleted(Integer id, boolean isDeleted);
}
