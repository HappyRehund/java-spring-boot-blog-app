package com.rehund.blog.controller;

import com.rehund.blog.entity.Post;
import com.rehund.blog.repository.PostRepository;
import com.rehund.blog.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentPublicControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Test
    void createComment_givenValid_shouldReturnCreated() throws Exception {
        Post post = new Post();
        post.setSlug("sluggy");
        post.setTitle("titlesluggy" );
        post.setCommentCount(1L);
        postRepository.save(post);
        mockMvc.perform(
                post("/api/public/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "rey",
                                    "email": "rey@gmail.com",
                                    "post": {
                                        "slug": "sluggy"
                                    },
                                    "body": "Kamu keren banget gilak"
                                }
                                """)

                )
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                        {
                            "name": "rey",
                            "email": "rey@gmail.com",
                            "post": {
                                "slug": "sluggy"
                            },
                            "body": "Kamu keren banget gilak"
                        }
                        """));
    }


}
