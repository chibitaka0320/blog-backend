package com.example.rakus_blog_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rakus_blog_backend.domain.User;
import com.example.rakus_blog_backend.domain.dto.UserDetailDTO;
import com.example.rakus_blog_backend.domain.dto.UserInArticle;
import com.example.rakus_blog_backend.form.UpdateUserForm;
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

    @GetMapping("/{userId}")
    public UserDetailDTO getUserDetail(@PathVariable Integer userId) {
        UserDetailDTO userDetail = userService.findByIdDetail(userId);
        return userDetail;
    }

    @PutMapping("/{userId}")
    public void update(@PathVariable Integer userId, @RequestBody UpdateUserForm form) {
        User user = new User();
        user.setName(form.getName());
        user.setIntroduction(form.getIntroduction());
        user.setImageUrl(createS3Url(form.getFileName()));
        user.setId(userId);

        userService.update(user);
    }

    private String createS3Url(String fileName) {
        String url = "https://rakus-blog-test.s3.ap-northeast-1.amazonaws.com/image/" + fileName;
        return url;
    }
}
