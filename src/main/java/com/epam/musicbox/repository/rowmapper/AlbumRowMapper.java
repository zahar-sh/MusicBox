package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Album row mapper.
 */
public class AlbumRowMapper implements RowMapper<Album> {

    private static final String ROW_ALBUM_ID = "album_id";
    private static final String ROW_NAME = "name";
    private static final String ROW_PICTURE = "picture";

    private static final AlbumRowMapper instance = new AlbumRowMapper();

    private AlbumRowMapper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AlbumRowMapper getInstance() {
        return instance;
    }

    @Override
    public Album map(ResultSet resultSet) throws RepositoryException {
        try {
            if (!resultSet.next()) {
                return null;
            }
            return new Album(resultSet.getLong(ROW_ALBUM_ID),
                    resultSet.getString(ROW_NAME),
                    resultSet.getString(ROW_PICTURE));
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}