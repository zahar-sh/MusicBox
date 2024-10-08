package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

public class AlbumGetByIdCommand implements Command {

    private static final String ALBUM_NOT_FOUND_MSG = "Album not found";

    private final AlbumService albumService = AlbumServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParameterTaker.getLong(body, Parameter.USER_ID);
            long albumId = ParameterTaker.getLong(req, Parameter.ALBUM_ID);

            boolean like = userService.isLikedAlbum(userId, albumId);
            req.setAttribute(Parameter.LIKE, like);

            Album album = albumService.findById(albumId)
                    .orElseThrow(() -> new CommandException(ALBUM_NOT_FOUND_MSG));
            req.setAttribute(Parameter.ALBUM, album);

            int page = ParameterTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
            int pageSize = ParameterTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);
            PageSearchResult<Track> psr = albumService.getTracks(albumId, page, pageSize);
            req.setAttribute(Parameter.TRACK_PAGE_SEARCH_RESULT, psr);

            return Router.forward(PagePath.ALBUM);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
