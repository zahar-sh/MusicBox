package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.ArtistRepository;
import com.epam.musicbox.repository.impl.ArtistRepositoryImpl;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.psr.PageSearchResult;
import com.epam.musicbox.validator.EntityValidator;
import com.epam.musicbox.validator.impl.EntityValidatorImpl;

import java.util.List;

public class ArtistServiceImpl extends AbstractEntityService<Artist> implements ArtistService {

    private static final ArtistServiceImpl instance = new ArtistServiceImpl();

    private final EntityValidator validator = EntityValidatorImpl.getInstance();
    private final ArtistRepository repository = ArtistRepositoryImpl.getInstance();

    private ArtistServiceImpl() {
    }

    public static ArtistServiceImpl getInstance() {
        return instance;
    }

    @Override
    protected ArtistRepository getRepository() {
        return repository;
    }

    @Override
    public long countByName(String name) throws ServiceException {
        try {
            return getRepository().countByName(buildRegex(name));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PageSearchResult<Artist> findByName(String name, int page, int pageSize) throws ServiceException {
        try {
            if (!validator.isValidName(name)) {
                return new PageSearchResult<>(page, pageSize);
            }

            String regex = buildRegex(name);
            ArtistRepository repository = getRepository();
            long count = repository.countByName(name);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            int offset = getOffset(page, pageSize);
            List<Artist> list = repository.findByName(regex, offset, pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countTracks(long artistId) throws ServiceException {
        try {
            return getRepository().countTracks(artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PageSearchResult<Track> getTracks(long artistId, int page, int pageSize) throws ServiceException {
        try {
            ArtistRepository repository = getRepository();
            long count = repository.countTracks(artistId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            int offset = getOffset(page, pageSize);
            List<Track> list = repository.getTracks(artistId, offset, pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean hasTrack(long artistId, long trackId) throws ServiceException {
        try {
            return getRepository().hasTrack(artistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addTrack(long artistId, long trackId) throws ServiceException {
        try {
            getRepository().addTrack(artistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeTrack(long artistId, long trackId) throws ServiceException {
        try {
            getRepository().removeTrack(artistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countAlbums(long artistId) throws ServiceException {
        try {
            return getRepository().countAlbums(artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PageSearchResult<Album> getAlbums(long artistId, int page, int pageSize) throws ServiceException {
        try {
            ArtistRepository repository = getRepository();
            long count = repository.countAlbums(artistId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            int offset = getOffset(page, pageSize);
            List<Album> list = repository.getAlbums(artistId, offset, pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
