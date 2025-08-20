package com.rehund.blog.response.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentResponse {
    private Integer id;
    private String name;
    private String email;
    private Post post;
    private String body;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String title;
        private String slug;
    }
}
