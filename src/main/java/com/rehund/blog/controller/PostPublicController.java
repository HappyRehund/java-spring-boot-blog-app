package com.rehund.blog.controller;

import com.rehund.blog.request.post.CreatePostRequest;
import com.rehund.blog.request.post.GetPostBySlugRequest;
import com.rehund.blog.request.post.GetPostsRequest;
import com.rehund.blog.request.post.UpdatePostBySlugRequest;
import com.rehund.blog.response.post.*;
import com.rehund.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/posts")
public class PostPublicController {

    private final PostService postService;

    @Autowired
    public PostPublicController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<GetPostResponse>> getPosts(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                                         @RequestParam(required = false, defaultValue = "5") Integer limit
                                             ){
        //only published post
        GetPostsRequest request = GetPostsRequest.builder()
                .pageNo(pageNo)
                .limit(limit)
                .build();
        return ResponseEntity.ok(postService.getPosts(request));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<GetPostResponse> getPostBySlug(@Valid @PathVariable String slug){
        //only published post
        GetPostBySlugRequest request = GetPostBySlugRequest.builder().slug(slug).build();
        return ResponseEntity.ok(postService.getPostBySlug(request));
    }
}
