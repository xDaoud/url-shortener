package com.xDaoud.url_shortener.repository;

import com.xDaoud.url_shortener.model.ShortLink;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Objects;

@Repository
public class ShortLinkRepository {
    private final JdbcTemplate jdbcTemplate;
    public ShortLinkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public ShortLink save(ShortLink shortLink) {
        String sql = "INSERT INTO short_links (url, hash) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con ->  {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, shortLink.getUrl());
            ps.setString(2, shortLink.getHash());
            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        shortLink.setId(id);
        return shortLink;
    }

    public ShortLink findByHash(String hash) {
        try {
            String sql = "SELECT * FROM short_links WHERE hash = ?";
            return jdbcTemplate.queryForObject(sql, new ShortLinkRowMapper(), hash);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
