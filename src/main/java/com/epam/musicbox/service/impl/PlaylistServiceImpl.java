package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.PlaylistRepository;
import com.epam.musicbox.repository.impl.PlaylistRepositoryImpl;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.page.PageSearchResult;

import java.util.List;

public class PlaylistServiceImpl extends AbstractEntityService<Playlist> implements PlaylistService {

    private static final PlaylistServiceImpl instance = new PlaylistServiceImpl();

    private final PlaylistRepository repository;

    private PlaylistServiceImpl() {
        repository = PlaylistRepositoryImpl.getInstance();
    }

    public static PlaylistServiceImpl getInstance() {
        return instance;
    }

    @Override
    protected PlaylistRepository getRepository() {
        return repository;
    }

    @Override
    public long countByName(String name) throws ServiceException {
        try {
            return getRepository().countByName(name);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageSearchResult<Playlist> findByName(String name, int page, int pageSize) throws ServiceException {
        try {
            if (!isValid(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }
            long count = getRepository().countByName(name);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            List<Playlist> list = getRepository().findByName(name, getOffset(page, pageSize), pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public long countTracks(long playlistId) throws ServiceException {
        try {
            return getRepository().countTracks(playlistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageSearchResult<Track> getTracks(long playlistId, int page, int pageSize) throws ServiceException {
        try {
            if (!isValid(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }
            long count = getRepository().countTracks(playlistId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            List<Track> list = getRepository().getTracks(playlistId, getOffset(page, pageSize), pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean hasTrack(long playlistId, long trackId) throws ServiceException {
        try {
            return getRepository().hasTrack(playlistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void addTrack(long playlistId, long trackId) throws ServiceException {
        try {
            getRepository().addTrack(playlistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void removeTrack(long playlistId, long trackId) throws ServiceException {
        try {
            getRepository().removeTrack(playlistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
