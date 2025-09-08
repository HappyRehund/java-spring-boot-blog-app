package com.rehund.blog.controller;

import com.rehund.blog.request.comment.CreateCommentRequest;
import com.rehund.blog.request.comment.GetCommentsRequest;
import com.rehund.blog.response.comment.CreateCommentResponse;
import com.rehund.blog.response.comment.GetCommentResponse;
import com.rehund.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/public/comments")
public class CommentPublicController {
    final private CommentService commentService;

    public CommentPublicController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public List<GetCommentResponse> getComments(@RequestParam String postSlug,
                                     @RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                     @RequestParam(required = false, defaultValue = "10") Integer limit){
        GetCommentsRequest request = GetCommentsRequest.builder()
                .postSlug(postSlug)
                .pageNo(pageNo)
                .limit(limit)
                .build();

        return commentService.getComments(request);
    }

    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(@Valid @RequestBody CreateCommentRequest comment){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(comment));
    }
}
