package com.rehund.blog.repository;

import com.rehund.blog.entity.Comment;
import com.rehund.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findFirstBySlugAndIsDeleted(String slug, boolean isDeleted);
    Optional<Post> findByIdAndIsDeleted(Integer id, boolean isDeleted);
    Page<Post> findByIsDeleted(boolean isDeleted, Pageable pageable);

}
