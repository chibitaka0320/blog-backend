package com.example.rakus_blog_backend.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.rakus_blog_backend.domain.Article;
import com.example.rakus_blog_backend.domain.User;
import com.example.rakus_blog_backend.repository.ArticleRepository;
import com.example.rakus_blog_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Integer insert(User user) {
        User deplicateUser = userRepository.findByEmail(user.getEmail());
        if (deplicateUser != null) {
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.insert(user);
    }

    public List<Article> getArticles(Integer userId) {
        return articleRepository.findByUserId(userId);
    }
}
