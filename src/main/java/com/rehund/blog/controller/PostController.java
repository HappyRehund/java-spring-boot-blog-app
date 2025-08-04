package com.rehund.blog.controller;

import com.rehund.blog.entity.Post;
import com.rehund.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired // <-- Spring otomatis inject PostService ke sini
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public Iterable<Post> getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/{slug}")
    public Post getPostBySlug(@PathVariable String slug){
        return postService.getPostBySlug(slug);
    }

    @PostMapping("/")
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PutMapping("/{slug}")
    public Post updatePostBySlug(@PathVariable String slug, @RequestBody Post post){
        return postService.updatePostBySlug(slug, post);
    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable Integer id) {
        postService.deletePostById(id);
    }

    @PostMapping("/{id}/publish")
    public Post publishPostWithId(@PathVariable Integer id) {
        return postService.publishPostWithId(id);
    }
}
