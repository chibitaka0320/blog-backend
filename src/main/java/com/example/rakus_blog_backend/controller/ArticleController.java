package com.example.rakus_blog_backend.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.rakus_blog_backend.domain.Article;
import com.example.rakus_blog_backend.domain.Comment;
import com.example.rakus_blog_backend.form.ArticleForm;
import com.example.rakus_blog_backend.form.CommentForm;
import com.example.rakus_blog_backend.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@CrossOrigin
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("")
    public List<Article> getArticles() {
        List<Article> articleList = articleService.findAll();
        if (articleList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return articleList;
    }

    @GetMapping("/{articleId}")
    public Article getArticle(@PathVariable Integer articleId) {
        Article article = articleService.load(articleId);
        if (article == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return article;
    }

    @PostMapping("")
    public ResponseEntity<Integer> insertArticle(@RequestBody ArticleForm form) {
        Article article = new Article();
        BeanUtils.copyProperties(form, article);

        Integer articleId = articleService.insert(article);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(articleId)
                .toUri();
        ResponseEntity<Integer> result = ResponseEntity.created(location).body(articleId);

        return result;
    }

    @PostMapping("/{articleId}/comment")
    public ResponseEntity<Void> insertComment(@RequestBody CommentForm form) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(form, comment);

        articleService.insertComment(comment);

        ResponseEntity<Void> result = ResponseEntity.created(null).build();
        return result;
    }
}
