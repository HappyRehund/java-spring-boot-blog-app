package com.rehund.blog.controller;

import com.rehund.blog.entity.Comment;
import com.rehund.blog.entity.Post;
import com.rehund.blog.repository.CommentRepository;
import com.rehund.blog.repository.PostRepository;
import com.rehund.blog.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentAdminControllerIntegrationTests {
    private final String HEADER_NAME = "Authorization";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    JwtService jwtService;

    private Comment savedComment;
    private Post savedPost;

    @BeforeEach
    void beforeEach(){
        commentRepository.deleteAll();
        postRepository.deleteAll();

        Post post = new Post();
        post.setSlug("sluggy");
        post.setTitle("titlesluggy" );
        post.setCommentCount(1L);
        this.savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setName("ray");

        this.savedComment = commentRepository.save(comment);
    }

    @Test
    void createComment_givenValid_shouldReturnCreated() throws Exception {
        String jwtToken = jwtService.generateTokenByUsername("ray");
        mockMvc.perform(get("/api/admin/comments/" + this.savedComment.getId())
                        .header(HEADER_NAME, "Bearer %s".formatted(jwtToken))
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": %s
                        }
                        """.formatted(this.savedComment.getId())));
    }
}
