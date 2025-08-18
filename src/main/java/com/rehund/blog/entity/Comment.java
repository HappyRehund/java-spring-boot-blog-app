package com.rehund.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private String name;
    private String email;
    private String postId;
    private String body;
    private Long createdAt;
}
