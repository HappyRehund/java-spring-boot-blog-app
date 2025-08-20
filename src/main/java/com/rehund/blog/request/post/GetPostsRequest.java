package com.rehund.blog.request.post;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostsRequest {
    private Integer pageNo;
    private Integer limit;
}
