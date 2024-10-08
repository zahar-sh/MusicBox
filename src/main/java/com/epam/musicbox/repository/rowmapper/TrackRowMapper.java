package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Track row mapper.
 */
public class TrackRowMapper implements RowMapper<Track> {

    private static final String ROW_TRACK_ID = "track_id";
    private static final String ROW_NAME = "name";
    private static final String ROW_AUDIO = "audio";
    private static final String ROW_ALBUM_ID = "album_id";

    private static final TrackRowMapper instance = new TrackRowMapper();

    private TrackRowMapper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TrackRowMapper getInstance() {
        return instance;
    }

    @Override
    public Track map(ResultSet resultSet) throws RepositoryException {
        try {
            if (!resultSet.next()) {
                return null;
            }
            return new Track(resultSet.getLong(ROW_TRACK_ID),
                    resultSet.getString(ROW_NAME),
                    resultSet.getString(ROW_AUDIO),
                    resultSet.getLong(ROW_ALBUM_ID));
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}