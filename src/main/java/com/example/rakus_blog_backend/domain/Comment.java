package com.example.rakus_blog_backend.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer commentId;
    private Integer articleId;
    private Integer userId;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
