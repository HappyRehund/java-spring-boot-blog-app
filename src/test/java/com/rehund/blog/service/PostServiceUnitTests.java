package com.rehund.blog.service;

import com.rehund.blog.entity.Post;
import com.rehund.blog.mapper.PostMapper;
import com.rehund.blog.repository.PostRepository;
import com.rehund.blog.request.post.CreatePostRequest;
import com.rehund.blog.response.post.CreatePostResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostServiceUnitTests {

    @Autowired
    @InjectMocks
    PostService postService;

    private AutoCloseable mocks;

    @BeforeEach
    void beforeEach(){
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception{
        mocks.close();
    }

    //  Kita perlu mock post repository supaya si postService tidak langsung memanggil postRepository asli
    @Mock
    PostRepository postRepository;

    @Test
    void createPost_givenValid_shouldReturnOk(){
        CreatePostRequest postRequest = new CreatePostRequest();

        postRequest.setTitle("postRequest title");
        postRequest.setSlug("postRequest slug");
        postRequest.setBody("This is postRequest body and the length must > 10");


        Post post = PostMapper.INSTANCE.mapFromCreatePostRequest(postRequest);
        post.setCommentCount(0L);
        post.setCreatedAt(Instant.now().getEpochSecond());
        when(postRepository.save(post)).thenReturn(post);

        CreatePostResponse postResponse = postService.createPost(postRequest);

        Assertions.assertNotNull(postResponse);
        Assertions.assertEquals(0, postResponse.getCommentCount());
        Assertions.assertEquals("postRequest slug", postResponse.getSlug());

        // check apakah efisien --> apakah memanggil save-nya satu kali saja?
        verify(postRepository, times(1)).save(post);
    }
}
