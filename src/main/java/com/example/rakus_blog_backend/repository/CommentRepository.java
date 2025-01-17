package com.example.rakus_blog_backend.repository;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.rakus_blog_backend.domain.Comment;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final NamedParameterJdbcTemplate template;

    public Integer insert(Comment comment) {
        String sql = "INSERT INTO comments(article_id, user_id, content) VALUES(:articleId, :userId, :content)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(comment);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] keyColumnNames = { "id" };
        template.update(sql, param, keyHolder, keyColumnNames);

        return keyHolder.getKey().intValue();
    }
}
