package com.rehund.blog.mapper;

import com.rehund.blog.entity.Comment;
import com.rehund.blog.request.comment.CreateCommentRequest;
import com.rehund.blog.response.comment.CreateCommentResponse;
import com.rehund.blog.response.comment.GetCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment mapFromCreateCommentRequest(CreateCommentRequest commentRequest);

    CreateCommentResponse mapToCreateCommentResponse(Comment comment);

    GetCommentResponse mapToGetCommentResponse(Comment comment);

}
