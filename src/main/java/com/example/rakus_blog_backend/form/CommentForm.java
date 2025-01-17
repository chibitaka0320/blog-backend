package com.example.rakus_blog_backend.form;

import lombok.Data;

@Data
public class CommentForm {
    private Integer articleId;
    private Integer userId;
    private String content;
}
