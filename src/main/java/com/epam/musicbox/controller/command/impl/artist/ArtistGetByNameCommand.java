package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import jakarta.servlet.http.HttpServletRequest;

public class ArtistGetByNameCommand implements Command {

    private static final String COMMAND = String.format("%s&%s=",
            CommandType.ALBUM_GET_BY_NAME.getName(),
            Parameter.NAME);

    private final ArtistService artistService = ArtistServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            String name = req.getParameter(Parameter.NAME);
            int page = ParameterTaker.getPage(req, Parameter.ARTIST_PAGE_INDEX);
            int pageSize = ParameterTaker.getPageSize(req, Parameter.ARTIST_PAGE_SIZE);
            PageSearchResult<Artist> psr = artistService.findByName(name, page, pageSize);
            req.setAttribute(Parameter.ARTIST_PAGE_SEARCH_RESULT, psr);
            req.setAttribute(Parameter.COMMAND, COMMAND + name);
            return Router.forward(PagePath.ARTISTS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
