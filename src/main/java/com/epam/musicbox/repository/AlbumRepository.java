package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;

public interface AlbumRepository extends Repository<Album> {

    long countByName(String regex) throws RepositoryException;

    List<Album> findByName(String regex, int offset, int limit) throws RepositoryException;
}
