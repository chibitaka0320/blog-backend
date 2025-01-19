package com.example.rakus_blog_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rakus_blog_backend.domain.dto.UserInArticle;
import com.example.rakus_blog_backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}/articles")
    public UserInArticle getUserArticles(@PathVariable Integer userId) {
        UserInArticle articleList = userService.getArticles(userId);
        return articleList;
    }
}
