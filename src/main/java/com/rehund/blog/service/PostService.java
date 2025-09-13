package com.rehund.blog.service;

import com.rehund.blog.entity.Category;
import com.rehund.blog.entity.Post;
import com.rehund.blog.exception.ApiException;
import com.rehund.blog.mapper.PostMapper;
import com.rehund.blog.repository.CategoryRepository;
import com.rehund.blog.repository.PostRepository;
import com.rehund.blog.request.post.CreatePostRequest;
import com.rehund.blog.request.post.GetPostBySlugRequest;
import com.rehund.blog.request.post.GetPostsRequest;
import com.rehund.blog.request.post.UpdatePostBySlugRequest;
import com.rehund.blog.response.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PostService(PostRepository postRepository, CategoryRepository categoryRepository){
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<GetPostResponse> getPosts(GetPostsRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNo(), request.getLimit());

        List<Post> posts = postRepository.findByIsDeleted(false, pageRequest).getContent();
        List<GetPostResponse> responses = new ArrayList<>();

        posts.forEach(post -> responses.add(PostMapper.INSTANCE.mapToGetPostResponse(post)));
        return responses;
    }
    public GetPostResponse getPostBySlug(GetPostBySlugRequest request){
        Post post = postRepository.findFirstBySlugAndIsDeleted(request.getSlug(), false)
                .orElseThrow(() -> new ApiException("Post tidak ditemukan", HttpStatus.NOT_FOUND));

        return PostMapper.INSTANCE.mapToGetPostResponse(post);
    }

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request){

        // sekarang mau assign dulu ke suatu category, berarti di body-nya bawa category
        Category category = categoryRepository.findByNameAndIsDeleted(request.getCategory().getName(), false)
                .orElseThrow(() -> new ApiException("category not found", HttpStatus.NOT_FOUND));

        Post post = PostMapper.INSTANCE.mapFromCreatePostRequest(request);

        post.setCommentCount(0L);
        post.setCreatedAt(Instant.now().getEpochSecond());
        post.setCategory(category);

        postRepository.save(post);

        return PostMapper.INSTANCE.mapToCreatePostResponse(post);
    }

    public UpdatePostBySlugResponse updatePostBySlug(String slug, UpdatePostBySlugRequest request){
        Post post =  postRepository.findFirstBySlugAndIsDeleted(slug, false).orElseThrow(() -> new ApiException("post not found", HttpStatus.NOT_FOUND));

        post.setTitle(request.getTitle());
        post.setBody(request.getBody());
        post.setSlug(request.getSlug());
        postRepository.save(post);

        return PostMapper.INSTANCE.mapToUpdatePostBySlugResponse(post);
    }

    public DeletePostByIdResponse deletePostById(Integer id){
        Post post =  postRepository.findById(id).orElseThrow(() -> new ApiException("post not found", HttpStatus.NOT_FOUND));

        post.setDeleted(true);
        postRepository.save(post);
        return PostMapper.INSTANCE.mapToDeletePostByIdResponse(post);
    }

    public PublishPostResponse publishPost(Integer id){
        Post post =  postRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new ApiException("post not found", HttpStatus.NOT_FOUND));

        post.setPublished(true);
        post.setPublishedAt(Instant.now().getEpochSecond());
        postRepository.save(post);

        return PublishPostResponse.builder().publishedAt(post.getPublishedAt()).isPublished(post.isPublished()).build();
    }

}
