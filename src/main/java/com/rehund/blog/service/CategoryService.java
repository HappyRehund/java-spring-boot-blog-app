package com.rehund.blog.service;

import com.rehund.blog.entity.Category;
import com.rehund.blog.exception.ApiException;
import com.rehund.blog.mapper.CategoryMapper;
import com.rehund.blog.repository.CategoryRepository;
import com.rehund.blog.repository.PostRepository;
import com.rehund.blog.request.category.CreateCategoryRequest;
import com.rehund.blog.request.category.GetCategoriesRequest;
import com.rehund.blog.request.category.UpdateCategoryRequest;
import com.rehund.blog.response.category.CreateCategoryResponse;
import com.rehund.blog.response.category.DeleteCategoryResponse;
import com.rehund.blog.response.category.GetCategoryResponse;
import com.rehund.blog.response.category.UpdateCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    final private CategoryRepository categoryRepository;
    final private PostRepository postRepository;

    @Autowired
    CategoryService(CategoryRepository categoryRepository, PostRepository postRepository){
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    public List<GetCategoryResponse> getCategories (GetCategoriesRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNo(), request.getLimit());

        List<Category> categories = categoryRepository.findAll(pageRequest).getContent();
        List<GetCategoryResponse> responses = new ArrayList<>();

        categories.forEach(category -> responses.add(CategoryMapper.INSTANCE.mapToGetCategoryResponse(category)));
        return responses;
    }

    public List<GetCategoryResponse> getCategoriesPublic (GetCategoriesRequest request) {

        // disini return yang isDeleted false
        PageRequest pageRequest = PageRequest.of(request.getPageNo(), request.getLimit());

        List<Category> categories = categoryRepository.findByIsDeleted(false, pageRequest).getContent();
        List<GetCategoryResponse> responses = new ArrayList<>();

        categories.forEach(category -> responses.add(CategoryMapper.INSTANCE.mapToGetCategoryResponse(category)));
        return responses;
    }

    public GetCategoryResponse getCategoryById (Integer id){

        Category category = categoryRepository.findByIdAndIsDeleted(id, false).orElseThrow(() -> new ApiException("category not found", HttpStatus.NOT_FOUND));

        return CategoryMapper.INSTANCE.mapToGetCategoryResponse(category);
    }

    public CreateCategoryResponse createCategory (CreateCategoryRequest request) {

        Category category = CategoryMapper.INSTANCE.mapFromCreateCategoryRequest(request);
        category.setCreatedAt(Instant.now().getEpochSecond());

        categoryRepository.save(category);

        return CategoryMapper.INSTANCE.mapToCreateCategoryResponse(category);
    }

    public UpdateCategoryResponse updateCategory(Integer id, UpdateCategoryRequest request) {

        Category category = CategoryMapper.INSTANCE.mapFromUpdateCategoryRequest(request);
        return CategoryMapper.INSTANCE.mapToUpdateCategoryResponse(category);
    }

    public DeleteCategoryResponse deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ApiException("category not found", HttpStatus.NOT_FOUND));

        Long numberOfPosts = postRepository.countByCategory(category);

        if (numberOfPosts == 0) {
            throw new ApiException("posts still exists cannot be deleted", HttpStatus.BAD_REQUEST);
        }

        category.setDeleted(true);
        categoryRepository.save(category);

        return CategoryMapper.INSTANCE.mapToDeleteCategoryResponse(category);
    }
}
