package com.rehund.blog.service;

import com.rehund.blog.entity.Comment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    public Iterable<Comment> getComments(String postSlug, Integer pageNo, Integer limit){
        List<Comment> commentList = new ArrayList<>();
        return commentList;
    }

    public Comment getCommentById(Integer id){
        return new Comment();
    }

    public Comment createComment(Comment comment){
        return comment;
    }
}
