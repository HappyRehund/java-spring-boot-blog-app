package com.rehund.blog.controller;

import com.rehund.blog.exception.ApiException;
import com.rehund.blog.request.comment.GetCommentsRequest;
import com.rehund.blog.response.comment.GetCommentResponse;
import com.rehund.blog.service.CommentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentPublicControllerUnitTests {

    @Autowired
    CommentPublicController controller;

    @MockitoBean
    CommentService commentService;

    private AutoCloseable mocks;

    @BeforeEach
    void beforeEach() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception {
        mocks.close();
    }


    @Test
    void getComments_givenOneComment_shouldReturnOne() {

        GetCommentsRequest request = GetCommentsRequest.builder()
                .postSlug("slug1")
                .pageNo(0)
                .limit(10)
                .build();

        GetCommentResponse getCommentResponse = new GetCommentResponse();
        getCommentResponse.setName("Ray");
        getCommentResponse.setEmail("Ray@gmail.com");
        getCommentResponse.setBody("This is comment filled by ray");
        getCommentResponse.setId(1);

        List<GetCommentResponse> actualCommentResponse =
                List.of(getCommentResponse);

        when(commentService.getComments(request)).thenReturn(actualCommentResponse);

        ResponseEntity<List<GetCommentResponse>> commentResponses = controller
                .getComments("slug1", 0, 10);

        Assertions.assertNotNull(commentResponses.getBody());
        Assertions.assertEquals(1, commentResponses.getBody().size());
        Assertions.assertEquals(1, commentResponses.getBody().getFirst().getId());
        Assertions.assertEquals("This is comment filled by ray", commentResponses.getBody().getFirst().getBody());
    }

    @Test
    void getComments_givenPostInvalid_shouldThrowError() {

        GetCommentsRequest request = GetCommentsRequest.builder()
                .postSlug("slug1")
                .pageNo(0)
                .limit(10)
                .build();

        when(commentService.getComments(request))
                .thenThrow(new ApiException("post not found", HttpStatus.NOT_FOUND));

        Assertions.assertThrows(ApiException.class, () -> controller
                .getComments("slug1", 0, 10));

    }
}
