package com.rehund.blog.request.post;

import com.rehund.blog.entity.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class CreatePostRequest {

    @Size(min=2, max = 100)
    @NotNull
    private String title;

    @Size(min = 10, max = 10_000)
    @NotNull
    private String body;

    @Size(min = 2, max = 100)
    @NotNull
    private String slug;

    @NotNull
    private Category category;
}
