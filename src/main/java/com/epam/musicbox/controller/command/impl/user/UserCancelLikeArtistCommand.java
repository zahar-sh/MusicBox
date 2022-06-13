package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.service.impl.*;
import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.*;
import com.epam.musicbox.util.Parameters;
import com.epam.musicbox.util.Commands;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UserCancelLikeArtistCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    private final ArtistService artistService = ArtistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        long artistId = Parameters.getLong(req, Parameter.ARTIST_ID);
        Optional<Artist> optional = artistService.findById(artistId);
        if (optional.isPresent()) {
            Artist artist = optional.get();
            req.setAttribute(Parameter.ARTIST, artist);

            Jws<Claims> jws = AuthServiceImpl.getInstance().getJws(req);
            Claims body = jws.getBody();
            long userId = Parameters.getLong(body, Parameter.USER_ID);

            userService.cancelLikeArtist(userId, artistId);
            req.setAttribute(Parameter.LIKE, false);

            Commands.handlePage(req, trackService, Parameter.TRACK_PAGE, Parameter.TRACK_LIST);
            Commands.handlePage(req, albumService, Parameter.ALBUM_PAGE, Parameter.ALBUM_LIST);
        } else {
            req.setAttribute(Parameter.ARTIST, null);
            req.setAttribute(Parameter.LIKE, null);

            Commands.savePageIndex(req, Parameter.TRACK_PAGE);
            Commands.savePageIndex(req, Parameter.ALBUM_PAGE);
        }
        return CommandResult.forward(PagePath.ARTIST);
    }
}
