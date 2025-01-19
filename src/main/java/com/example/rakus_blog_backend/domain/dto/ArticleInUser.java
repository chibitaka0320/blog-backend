package com.example.rakus_blog_backend.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.rakus_blog_backend.domain.Article;
import com.example.rakus_blog_backend.domain.Comment;
import com.example.rakus_blog_backend.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleInUser extends Article {

    private User user;

    public ArticleInUser(Integer articleId, Integer userId, String userName, String title, String content,
            LocalDateTime created_at, LocalDateTime updatedAt, List<Comment> commentList, User user) {
        super(articleId, userId, userName, title, content, created_at, updatedAt, commentList);
        user = this.user;
    }
}
