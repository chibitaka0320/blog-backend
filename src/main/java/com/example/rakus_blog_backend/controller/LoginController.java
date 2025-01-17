package com.example.rakus_blog_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.rakus_blog_backend.domain.User;
import com.example.rakus_blog_backend.form.LoginForm;
import com.example.rakus_blog_backend.form.RegisterForm;
import com.example.rakus_blog_backend.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("")
@CrossOrigin
@RequiredArgsConstructor
public class LoginController {

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm form, HttpServletResponse response) {
        try {
            daoAuthenticationProvider
                    .authenticate(new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword()));
            User user = userService.findByEmail(form.getEmail());
            String token = JWT.create()
                    .withClaim("id", user.getId())
                    .withClaim("name", user.getName())
                    .sign(Algorithm.HMAC256("__secret__"));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("X-AUTH-TOKEN", token);
            Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return new ResponseEntity<String>(httpHeaders, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody @Validated RegisterForm form, BindingResult result,
            HttpServletResponse response) {
        User user = new User();
        BeanUtils.copyProperties(form, user);
        Integer userId = userService.insert(user);

        if (userId != null) {
            String token = JWT.create()
                    .withClaim("id", userId)
                    .withClaim("name", user.getName())
                    .sign(Algorithm.HMAC256("__secret__"));
            // HttpHeaders httpHeaders = new HttpHeaders();
            // httpHeaders.add("X-AUTH-TOKEN", token);
            // return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
            Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
            response.addCookie(cookie);
            return ResponseEntity.ok("登録に成功しました");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("メールアドレスが重複しています");
        }
    }
}
