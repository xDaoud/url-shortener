package com.xDaoud.url_shortener.repository;

import com.xDaoud.url_shortener.model.ShortLink;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShortLinkRowMapper  implements RowMapper<ShortLink> {
    @Override
    public ShortLink mapRow(ResultSet resultSet, int rowNumber) throws SQLException{
        ShortLink shortLink = new ShortLink();
        shortLink.setId(resultSet.getLong("id"));
        shortLink.setHash(resultSet.getString("hash"));
        shortLink.setUrl(resultSet.getString("url"));
        return shortLink;
    }
}
