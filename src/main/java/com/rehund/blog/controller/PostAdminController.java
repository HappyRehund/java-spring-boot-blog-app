package com.rehund.blog.controller;

import com.rehund.blog.request.post.CreatePostRequest;
import com.rehund.blog.request.post.GetPostBySlugRequest;
import com.rehund.blog.request.post.GetPostsRequest;
import com.rehund.blog.request.post.UpdatePostBySlugRequest;
import com.rehund.blog.response.post.*;
import com.rehund.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/posts")
public class PostAdminController {

    private final PostService postService;

    @Autowired
    public PostAdminController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<GetPostResponse>> getPosts(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                                         @RequestParam(required = false, defaultValue = "5") Integer limit
                                             ){
        GetPostsRequest request = GetPostsRequest.builder()
                .pageNo(pageNo)
                .limit(limit)
                .build();
        return ResponseEntity.ok(postService.getPosts(request));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<GetPostResponse> getPostBySlug(@Valid @PathVariable String slug){
        GetPostBySlugRequest request = GetPostBySlugRequest.builder().slug(slug).build();
        return ResponseEntity.ok(postService.getPostBySlug(request));
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(createPostRequest));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<UpdatePostBySlugResponse> updatePostBySlug(@PathVariable String slug,
                                                     @Valid @RequestBody UpdatePostBySlugRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.updatePostBySlug(slug, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletePostByIdResponse> deletePostById(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.deletePostById(id));
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<PublishPostResponse> publishPost(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.publishPost(id));
    }
}
