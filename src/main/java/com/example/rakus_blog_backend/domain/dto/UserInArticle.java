package com.example.rakus_blog_backend.domain.dto;

import java.util.List;

import com.example.rakus_blog_backend.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInArticle {

    private Integer userId;
    private String userName;
    private String introduction;
    private List<Article> articleList;
}
