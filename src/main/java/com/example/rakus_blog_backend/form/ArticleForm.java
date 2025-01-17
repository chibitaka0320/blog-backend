package com.example.rakus_blog_backend.form;

import lombok.Data;

@Data
public class ArticleForm {
    private Integer userId;
    private String title;
    private String content;
}
