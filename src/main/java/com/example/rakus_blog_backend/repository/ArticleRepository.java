package com.example.rakus_blog_backend.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.rakus_blog_backend.domain.Article;
import com.example.rakus_blog_backend.domain.Comment;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@CrossOrigin
public class ArticleRepository {
    private final NamedParameterJdbcTemplate template;

    private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
        Article article = new Article();
        article.setArticleId(rs.getInt("id"));
        article.setUserId(rs.getInt("user_id"));
        article.setUserName(rs.getString("name"));
        article.setTitle(rs.getString("title"));
        article.setContent(rs.getString("content"));
        article.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        article.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return article;
    };

    private static final ResultSetExtractor<List<Article>> ARTICLE_RESULT_SET_EXTRACTOR = (rs) -> {

        List<Article> articleList = new ArrayList<>();
        List<Comment> commentList = null;

        // 前回のarticleIdを入れる
        int pid = -1;

        while (rs.next()) {
            // 現在のarticleIdを代入
            int nowId = rs.getInt("a_id");

            if (nowId != pid) {
                Article article = new Article();
                article.setArticleId(nowId);
                article.setUserId(rs.getInt("a_user_id"));
                article.setUserName(rs.getString("a_user_name"));
                article.setTitle(rs.getString("a_title"));
                article.setContent(rs.getString("a_content"));
                article.setCreatedAt(rs.getTimestamp("a_created").toLocalDateTime());
                article.setUpdatedAt(rs.getTimestamp("a_updated").toLocalDateTime());

                commentList = new ArrayList<>();
                article.setCommentList(commentList);

                articleList.add(article);
            }

            if (rs.getInt("c_id") != 0) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("c_id"));
                comment.setUserId(rs.getInt("c_user_id"));
                comment.setUserName(rs.getString("c_user_name"));
                comment.setContent(rs.getString("c_content"));
                comment.setCreatedAt(rs.getTimestamp("c_created").toLocalDateTime());
                comment.setUpdatedAt(rs.getTimestamp("c_updated").toLocalDateTime());

                commentList.add(comment);
            }

            pid = nowId;
        }

        return articleList;
    };

    public List<Article> findAll() {
        String sql = """
                SELECT
                    a.id,
                    a.user_id,
                    u.name,
                    a.title,
                    a.content,
                    a.created_at,
                    a.updated_at
                FROM articles a
                JOIN users u ON a.user_id = u.id
                ORDER BY a.updated_at DESC;
                """;
        List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
        return articleList;
    }

    public Article load(Integer articleId) {
        String sql = """
                SELECT
                    a.id a_id,
                    a.user_id a_user_id,
                    au.name a_user_name,
                    a.title a_title,
                    a.content a_content,
                    a.created_at a_created,
                    a.updated_at a_updated,
                    c.id c_id,
                    c.user_id c_user_id,
                    cu.name c_user_name,
                    c.content c_content,
                    c.created_at c_created,
                    c.updated_at c_updated
                FROM articles a
                JOIN users au ON a.user_id = au.id
                LEFT JOIN comments c ON a.id = c.article_id
                LEFT JOIN users cu ON c.user_id = cu.id
                WHERE a.id = :articleId
                ORDER BY c.created_at;
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
        try {
            List<Article> articleList = template.query(sql, param, ARTICLE_RESULT_SET_EXTRACTOR);
            return articleList.get(0);
        } catch (DataAccessException e) {
            return null;
        }
    }

    public Integer insert(Article article) {
        String sql = "INSERT INTO articles(user_id, title, content) VALUES(:userId, :title, :content)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] keyColumnNames = { "id" };
        template.update(sql, param, keyHolder, keyColumnNames);

        return keyHolder.getKey().intValue();
    }

    public List<Article> findByUserId(Integer userId) {
        String sql = """
                SELECT
                    a.id,
                    a.user_id,
                    u.name,
                    a.title,
                    a.content,
                    a.created_at,
                    a.updated_at
                FROM articles a
                JOIN users u ON a.user_id = u.id
                WHERE a.user_id = :userId
                ORDER BY a.updated_at DESC;
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
        List<Article> articleList = template.query(sql, param, ARTICLE_ROW_MAPPER);
        return articleList;
    }
}
