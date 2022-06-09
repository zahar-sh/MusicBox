package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.Parameters;
import com.epam.musicbox.util.Services;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class UserGetPlaylistsCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        long userId = Services.getUserIdFromReqOrJws(req);
        int playlistPage = Parameters.getIntOrZero(req, Parameter.PLAYLIST_PAGE);
        List<Playlist> playlists = service.getPlaylists(userId, playlistPage);
        req.setAttribute(Parameter.PLAYLIST_LIST, playlists);
        return CommandResult.forward(PagePath.PLAYLISTS);
    }
}
