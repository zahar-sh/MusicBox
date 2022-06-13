package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.Parameters;
import com.epam.musicbox.util.Commands;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class UserGetLikedAlbumsCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        long userId = Commands.getUserIdFromReqOrJws(req);
        int page = Parameters.getIntOrZero(req, Parameter.ALBUM_PAGE);
        List<Album> list = service.getLikedAlbums(userId, page);
        req.setAttribute(Parameter.ALBUM_PAGE, page);
        req.setAttribute(Parameter.ALBUM_LIST, list);
        return CommandResult.forward(PagePath.ALBUMS);
    }
}
