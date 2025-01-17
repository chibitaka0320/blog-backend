package com.example.rakus_blog_backend.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String introduction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
