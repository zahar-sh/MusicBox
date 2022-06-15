package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class GoToUserPageCommand extends GoToPageCommand {

    private static final String USER_NOT_FOUND_MSG = "User not found";

    private final UserService userService = UserServiceImpl.getInstance();

    public GoToUserPageCommand() {
        super(PagePath.USER);
    }

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        Jws<Claims> jws = AuthServiceImpl.getInstance().getJws(req);
        Claims body = jws.getBody();
        long userId = ParamTaker.getLong(body, Parameter.USER_ID);

        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ServiceException(USER_NOT_FOUND_MSG);
        }

        User user = optionalUser.get();
        req.setAttribute(Parameter.USER, user);

        Role role = ParamTaker.getRole(body);
        if (role == Role.ADMIN) {
            req.setAttribute(Parameter.ADMIN, userId);
        }

        return super.execute(req, resp);
    }
}
