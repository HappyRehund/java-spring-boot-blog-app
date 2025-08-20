package com.rehund.blog.request.post;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class UpdatePostBySlugRequest {
    @Size(min = 2)
    @NotNull
    private String title;

    @Size(min = 10)
    @NotNull
    private String body;

    @Size(min = 2)
    @NotNull
    private String slug;
}
