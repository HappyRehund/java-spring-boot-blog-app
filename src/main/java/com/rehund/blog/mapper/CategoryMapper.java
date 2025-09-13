package com.rehund.blog.mapper;

import com.rehund.blog.entity.Category;
import com.rehund.blog.request.category.CreateCategoryRequest;
import com.rehund.blog.request.category.UpdateCategoryRequest;
import com.rehund.blog.response.category.CreateCategoryResponse;
import com.rehund.blog.response.category.DeleteCategoryResponse;
import com.rehund.blog.response.category.GetCategoryResponse;
import com.rehund.blog.response.category.UpdateCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category mapFromCreateCategoryRequest(CreateCategoryRequest createCategoryRequest);

    Category mapFromUpdateCategoryRequest(UpdateCategoryRequest updateCategoryRequest);

    CreateCategoryResponse mapToCreateCategoryResponse(Category category);

    GetCategoryResponse mapToGetCategoryResponse(Category category);

    UpdateCategoryResponse mapToUpdateCategoryResponse(Category category);

    DeleteCategoryResponse mapToDeleteCategoryResponse(Category category);
}
