package com.rehund.blog.controller;

import com.rehund.blog.request.comment.CreateCommentRequest;
import com.rehund.blog.request.comment.GetCommentByIdRequest;
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
@RequestMapping("/api/admin/comments")
public class CommentAdminController {
    final private CommentService commentService;

    public CommentAdminController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<GetCommentResponse>> getComments(@RequestParam(required = false) String postSlug,
                                     @RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                     @RequestParam(required = false, defaultValue = "10") Integer limit){
        GetCommentsRequest request = GetCommentsRequest.builder()
                .postSlug(postSlug)
                .pageNo(pageNo)
                .limit(limit)
                .build();

        return ResponseEntity.ok(commentService.getComments(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCommentResponse> getComment(@PathVariable Integer id){
        GetCommentByIdRequest request = GetCommentByIdRequest.builder().id(id).build();
        return ResponseEntity.ok(commentService.getCommentById(request));
    }

    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(@Valid @RequestBody CreateCommentRequest comment){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(comment));
    }
}
