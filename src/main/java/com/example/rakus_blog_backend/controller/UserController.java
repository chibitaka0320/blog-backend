package com.example.rakus_blog_backend.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rakus_blog_backend.domain.Article;
import com.example.rakus_blog_backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/articles")
    public List<Article> getUserArticles() {
        Integer id = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Article> articleList = userService.getArticles(id);
        return articleList;
    }
}
