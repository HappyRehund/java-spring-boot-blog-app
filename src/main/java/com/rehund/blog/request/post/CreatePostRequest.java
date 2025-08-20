package com.rehund.blog.request.post;

import jakarta.validation.Valid;
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

    @Size(min=2)
    private String title;
    @Size(min = 10)
    private String body;
    @Size(min = 2)
    private String slug;
}
