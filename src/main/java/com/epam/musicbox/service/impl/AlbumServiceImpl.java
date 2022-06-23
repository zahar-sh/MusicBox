package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.AlbumRepository;
import com.epam.musicbox.repository.impl.AlbumRepositoryImpl;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.psr.PageSearchResult;
import com.epam.musicbox.validator.EntityValidator;
import com.epam.musicbox.validator.impl.EntityValidatorImpl;

import java.util.List;

public class AlbumServiceImpl extends AbstractEntityService<Album> implements AlbumService {

    private static final AlbumServiceImpl instance = new AlbumServiceImpl();

    private final EntityValidator validator = EntityValidatorImpl.getInstance();
    private final AlbumRepository repository = AlbumRepositoryImpl.getInstance();

    private AlbumServiceImpl() {
    }

    public static AlbumServiceImpl getInstance() {
        return instance;
    }

    @Override
    protected AlbumRepository getRepository() {
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
    public PageSearchResult<Album> findByName(String name, int page, int pageSize) throws ServiceException {
        try {
            if (!validator.isValidName(name)) {
                return new PageSearchResult<>(page, pageSize);
            }

            String regex = buildRegex(name);
            AlbumRepository repository = getRepository();
            long count = repository.countByName(name);
            if (count == 0 || !isValidPage(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }
            int offset = getOffset(page, pageSize);
            List<Album> list = repository.findByName(regex, offset, pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
