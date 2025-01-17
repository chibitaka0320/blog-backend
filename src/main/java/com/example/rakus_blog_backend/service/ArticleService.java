package com.example.rakus_blog_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.rakus_blog_backend.domain.Article;
import com.example.rakus_blog_backend.domain.Comment;
import com.example.rakus_blog_backend.repository.ArticleRepository;
import com.example.rakus_blog_backend.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article load(Integer articleId) {
        return articleRepository.load(articleId);
    }

    public Integer insert(Article article) {
        return articleRepository.insert(article);
    }

    public Integer insertComment(Comment comment) {
        return commentRepository.insert(comment);
    }
}
