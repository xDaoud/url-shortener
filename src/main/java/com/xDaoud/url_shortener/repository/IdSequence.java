package com.xDaoud.url_shortener.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IdSequence {
    private final JdbcTemplate jdbcTemplate;
    public IdSequence(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public long nextId() {
        return jdbcTemplate.queryForObject("SELECT nextval('url_seq')", Long.class);
    }
}
