package com.example.rakus_blog_backend.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.rakus_blog_backend.domain.Article;
import com.example.rakus_blog_backend.domain.User;
import com.example.rakus_blog_backend.domain.dto.UserDetailDTO;
import com.example.rakus_blog_backend.domain.dto.UserInArticle;
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

    public UserDetailDTO findByIdDetail(Integer userId) {
        User user = userRepository.findByUser(userId);
        UserDetailDTO userDetailDTO = new UserDetailDTO();

        BeanUtils.copyProperties(user, userDetailDTO);

        return userDetailDTO;
    }

    public Integer insert(User user) {
        User deplicateUser = userRepository.findByEmail(user.getEmail());
        if (deplicateUser != null) {
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.insert(user);
    }

    public UserInArticle getArticles(Integer userId) {
        User user = userRepository.findByUser(userId);

        if (user == null) {
            return null;
        } else {
            List<Article> articleList = articleRepository.findByUserId(userId);
            UserInArticle userInArticle = new UserInArticle(user.getId(), user.getName(), user.getIntroduction(),
                    articleList);
            return userInArticle;
        }
    }

    public void update(User user) {
        userRepository.update(user);
    }
}
