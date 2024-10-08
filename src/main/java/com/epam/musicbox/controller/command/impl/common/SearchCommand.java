package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import jakarta.servlet.http.HttpServletRequest;

public class SearchCommand implements Command {

    private static final int FIRST_PAGE = 1;
    private static final int PAGE_SIZE = 10;

    private final ArtistService artistService = ArtistServiceImpl.getInstance();
    private final AlbumService albumService = AlbumServiceImpl.getInstance();
    private final TrackService trackService = TrackServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            PageSearchResult<Artist> artistPsr;
            PageSearchResult<Album> albumPsr;
            PageSearchResult<Track> trackPsr;

            String name = req.getParameter(Parameter.NAME);
            if (name != null && !name.trim().isEmpty()) {
                artistPsr = artistService.findByName(name, FIRST_PAGE, PAGE_SIZE);
                albumPsr = albumService.findByName(name, FIRST_PAGE, PAGE_SIZE);
                trackPsr = trackService.findByName(name, FIRST_PAGE, PAGE_SIZE);
            } else {
                artistPsr = new PageSearchResult<>(FIRST_PAGE, PAGE_SIZE);
                albumPsr = new PageSearchResult<>(FIRST_PAGE, PAGE_SIZE);
                trackPsr = new PageSearchResult<>(FIRST_PAGE, PAGE_SIZE);
            }

            req.setAttribute(Parameter.ARTIST_PAGE_SEARCH_RESULT, artistPsr);
            req.setAttribute(Parameter.ALBUM_PAGE_SEARCH_RESULT, albumPsr);
            req.setAttribute(Parameter.TRACK_PAGE_SEARCH_RESULT, trackPsr);

            req.setAttribute(Parameter.NAME, req.getParameter(Parameter.NAME));

            return Router.forward(PagePath.SEARCH);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
