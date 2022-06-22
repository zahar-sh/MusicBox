package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class TrackGetByIdCommand implements Command {

    private static final String TRACK_NOT_FOUND_MSG = "Track not found";

    private final TrackService trackService = TrackServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();
    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParamTaker.getLong(body, Parameter.USER_ID);
            long trackId = ParamTaker.getLong(req, Parameter.TRACK_ID);

            boolean like = userService.isLikedTrack(userId, trackId);
            req.setAttribute(Parameter.LIKE, like);

            Track track = trackService.findById(trackId)
                    .orElseThrow(() -> new CommandException(TRACK_NOT_FOUND_MSG));
            req.setAttribute(Parameter.TRACK, track);

            Optional<Album> optionalAlbum = albumService.findById(track.getAlbumId());
            Album album = optionalAlbum.orElse(null);
            req.setAttribute(Parameter.ALBUM, album);
            return CommandResult.forward(PagePath.TRACK);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
