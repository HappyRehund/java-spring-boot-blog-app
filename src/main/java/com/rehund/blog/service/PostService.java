package com.rehund.blog.service;

import com.rehund.blog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {
    Post post1 = new Post(1, "title1", "slug1");
    Post post2 = new Post(2, "title2", "slug2");
    @Getter
    List<Post> posts = new ArrayList<Post>(Arrays.asList(post1, post2));

    public Post getPostBySlug(String slug){
        return posts.stream().filter(post -> post.getSlug().equals(slug)).findFirst().orElse(null);
    }

    public Post createPost(Post post){
        posts.add(post);
        return post;
    }

    public Post updatePostBySlug(String slug, Post post){
        Post savedPost =  posts.stream().filter(p -> p.getSlug().equals(slug)).findFirst().orElse(null);
        if(savedPost == null) {
            return null;
        }
        posts.remove(savedPost);
        savedPost.setTitle(post.getTitle());
        savedPost.setSlug(post.getSlug());
        posts.add(savedPost);
        return savedPost;
    }

    public void deletePostById(Integer id){
        Post savedPost =  posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if(savedPost == null){
            System.out.println("Post not found");
        }

        posts.remove(savedPost);
        System.out.println("Delete post success");
    }

    public Post publishPostWithId(Integer id){
        Post savedPost =  posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if(savedPost == null){
            return null;
        }
        savedPost.setPublished(true);
        return savedPost;
    }
}
