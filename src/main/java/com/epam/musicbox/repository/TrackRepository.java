package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;

public interface TrackRepository extends Repository<Track> {

    long countByName(String regex) throws RepositoryException;

    List<Track> findByName(String regex, int offset, int limit) throws RepositoryException;
}
