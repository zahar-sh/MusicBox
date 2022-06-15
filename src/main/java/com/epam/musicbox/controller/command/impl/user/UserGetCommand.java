package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class UserGetCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        int page = ParamTaker.getPage(req, Parameter.USER_PAGE);
        int pageSize = ParamTaker.getInt(req, Parameter.USER_PAGE_SIZE);
        List<User> users = userService.findPage(page, pageSize);
        req.setAttribute(Parameter.USER_PAGE, page);
        req.setAttribute(Parameter.USER_LIST, users);
        return CommandResult.forward(PagePath.USERS);
    }
}
