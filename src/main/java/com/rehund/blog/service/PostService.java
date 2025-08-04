package com.rehund.blog.service;

import com.rehund.blog.entity.Post;
import com.rehund.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {

    @Autowired
    PostRepository postRepository;

    public Iterable<Post> getPosts() {
        return postRepository.findAll();
    }
    public Post getPostBySlug(String slug){
        return postRepository.findFirstBySlugAndIsDeleted(slug, false).orElse(null);
    }

    public Post createPost(Post post){
        post.setCreatedAt(Instant.now().getEpochSecond());
        return postRepository.save(post);
    }

    public Post updatePostBySlug(String slug, Post post){
        Post foundPost =  postRepository.findFirstBySlugAndIsDeleted(slug, false).orElse(null);
        if(foundPost == null) {
            return null;
        }
        post.setId(foundPost.getId());
        return postRepository.save(post);
    }

    public boolean deletePostById(Integer id){
        Post post =  postRepository.findById(id).orElse(null);
        if(post == null){
            return false;
        }
        post.setDeleted(true);
        postRepository.save(post);
        return true;
    }

    public Post publishPostWithId(Integer id){
        Post post =  postRepository.findById(id).orElse(null);
        if(post == null){
            return null;
        }
        post.setPublished(true);
        post.setPublishedAt(Instant.now().getEpochSecond());
        return postRepository.save(post);
    }

}
