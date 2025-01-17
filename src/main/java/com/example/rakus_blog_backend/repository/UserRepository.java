package com.example.rakus_blog_backend.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.rakus_blog_backend.domain.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final NamedParameterJdbcTemplate template;

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return user;
    };

    public User findByEmail(String email) {
        String sql = "SELECT id, name, password, email, created_at, updated_at FROM users WHERE email = :email";
        SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);

        try {
            User user = template.queryForObject(sql, param, USER_ROW_MAPPER);
            return user;
        } catch (DataAccessException e) {
            return null;
        }
    }

    public Integer insert(User user) {
        String sql = "INSERT INTO users(name, email, password) VALUES(:name, :email, :password)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(user);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] keyColumnNames = { "id" };

        template.update(sql, param, keyHolder, keyColumnNames);

        return keyHolder.getKey().intValue();
    }
}
