package com.rehund.blog.mapper;

import com.rehund.blog.entity.Post;
import com.rehund.blog.request.post.CreatePostRequest;
import com.rehund.blog.response.post.CreatePostResponse;
import com.rehund.blog.response.post.DeletePostByIdResponse;
import com.rehund.blog.response.post.GetPostResponse;
import com.rehund.blog.response.post.UpdatePostBySlugResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post mapFromCreatePostRequest(CreatePostRequest postRequest);

    CreatePostResponse mapToCreatePostResponse(Post post);

    GetPostResponse mapToGetPostResponse(Post post);

    UpdatePostBySlugResponse mapToUpdatePostBySlugResponse(Post post);

    DeletePostByIdResponse mapToDeletePostByIdResponse(Post post);
}