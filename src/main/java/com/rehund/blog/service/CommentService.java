package com.rehund.blog.service;

import com.rehund.blog.entity.Comment;
import com.rehund.blog.entity.Post;
import com.rehund.blog.exception.ApiException;
import com.rehund.blog.mapper.CommentMapper;
import com.rehund.blog.mapper.PostMapper;
import com.rehund.blog.repository.CommentRepository;
import com.rehund.blog.repository.PostRepository;
import com.rehund.blog.request.comment.CreateCommentRequest;
import com.rehund.blog.request.comment.GetCommentByIdRequest;
import com.rehund.blog.request.comment.GetCommentsRequest;
import com.rehund.blog.response.comment.CreateCommentResponse;
import com.rehund.blog.response.comment.GetCommentResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<GetCommentResponse> getComments(GetCommentsRequest request){
        Post post = postRepository.findFirstBySlugAndIsDeleted(request.getPostSlug(), false).orElseThrow(() -> new ApiException("post not found", HttpStatus.NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(request.getPageNo(), request.getLimit());
        List<Comment> comments = commentRepository.findByPostId(post.getId(), pageRequest).getContent();
        List<GetCommentResponse> responses = new ArrayList<>();

        comments.forEach(comment -> responses.add(CommentMapper.INSTANCE.mapToGetCommentResponse(comment)));
        return responses;
    }

    public GetCommentResponse getCommentById(GetCommentByIdRequest request){

        Comment comment = commentRepository.findById(request.getId()).orElseThrow(() -> new ApiException("comment not found", HttpStatus.NOT_FOUND));

        return CommentMapper.INSTANCE.mapToGetCommentResponse(comment);
    }

    @Transactional
    public CreateCommentResponse createComment(CreateCommentRequest request){
        // di request body ada post --> slug
        Post post = postRepository.findFirstBySlugAndIsDeleted(request.getPost().getSlug(), false)
                .orElseThrow(() -> new ApiException("post not found", HttpStatus.NOT_FOUND));

        Comment comment = CommentMapper.INSTANCE.mapFromCreateCommentRequest(request);

        comment.setCreatedAt(Instant.now().getEpochSecond());
        comment.setPost(post);

        commentRepository.save(comment);

        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);

        return CommentMapper.INSTANCE.mapToCreateCommentResponse(comment);
    }
}
