package com.rehund.blog.controller;

import com.rehund.blog.request.post.CreatePostRequest;
import com.rehund.blog.request.post.GetPostBySlugRequest;
import com.rehund.blog.request.post.GetPostsRequest;
import com.rehund.blog.request.post.UpdatePostBySlugRequest;
import com.rehund.blog.response.post.*;
import com.rehund.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<GetPostResponse> getPosts(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                             @RequestParam(required = false, defaultValue = "5") Integer limit
                                             ){
        GetPostsRequest request = GetPostsRequest.builder()
                .pageNo(pageNo)
                .limit(limit)
                .build();
        return postService.getPosts(request);
    }

    @GetMapping("/{slug}")
    public GetPostResponse getPostBySlug(@Valid @PathVariable String slug){
        GetPostBySlugRequest request = GetPostBySlugRequest.builder().slug(slug).build();
        return postService.getPostBySlug(request);
    }

    @PostMapping
    public CreatePostResponse createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest);
    }

    @PutMapping("/{slug}")
    public UpdatePostBySlugResponse updatePostBySlug(@PathVariable String slug,
                                                     @Valid @RequestBody UpdatePostBySlugRequest request){
        return postService.updatePostBySlug(slug, request);
    }

    @DeleteMapping("/{id}")
    public DeletePostByIdResponse deletePostById(@PathVariable Integer id) {
        return postService.deletePostById(id);
    }

    @PutMapping("/{id}/publish")
    public PublishPostResponse publishPost(@PathVariable Integer id) {
        return postService.publishPost(id);
    }
}
