package com.rehund.blog.response.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentResponse {
    private String name;
    private String email;
    private Post post;
    private String body;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private String title;
        private String slug;
    }
}
