package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.util.AuthUtils;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserCancelLikeAlbumCommand implements Command {
    @Inject
    private UserService userService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        Jws<Claims> claimsJws = AuthUtils.getClaimsJws(req);
        Claims body = claimsJws.getBody();
        long userId = Parameters.get(body, Parameter.USER_ID);
        long albumId = Parameters.getLong(req, Parameter.ALBUM_ID);
        userService.cancelLikeAlbum(userId, albumId);
    }
}
