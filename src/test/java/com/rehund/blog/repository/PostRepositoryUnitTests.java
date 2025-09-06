package com.rehund.blog.repository;

import com.rehund.blog.entity.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
public class PostRepositoryUnitTests {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void beforeEach(){
        postRepository.deleteAll();
    }

    @AfterEach
    void afterEach(){
        postRepository.deleteAll();
    }

//  Format : Nama method_situasi_expected
    @Test
    void FindByIsDeleted_givenNoPosts_shouldReturnEmpty(){
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<Post> posts = postRepository.findByIsDeleted(false, pageRequest).getContent();
        Assertions.assertNotNull(posts);
        Assertions.assertEquals(0, posts.size());
    }

    @Test
    void FindByIsDeleted_givenTwoPostsOnlyOneNotDeletedYet_shouldReturnOnlyOne(){
        PageRequest pageRequest = PageRequest.of(0, 5);

        Post post1 = new Post();
        post1.setDeleted(true);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setDeleted(false);
        postRepository.save(post2);

        List<Post> posts = postRepository.findByIsDeleted(false, pageRequest).getContent();
        Assertions.assertNotNull(posts);
        Assertions.assertEquals(1, posts.size());
    }
}
