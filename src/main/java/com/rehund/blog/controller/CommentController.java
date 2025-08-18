package com.rehund.blog.controller;

import com.rehund.blog.entity.Comment;
import com.rehund.blog.service.CommentService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/comments")
public class CommentController {
    final private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public Iterable<Comment> getComments(@RequestParam(required = false) String postSlug,
                                         @RequestParam(required = false) Integer pageNo,
                                         @RequestParam(required = false) Integer limit){
        return commentService.getComments(postSlug, pageNo, limit);
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable Integer id){
        return commentService.getCommentById(id);
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment){
        return commentService.createComment(comment);
    }
}
