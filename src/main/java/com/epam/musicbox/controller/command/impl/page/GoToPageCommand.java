package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public abstract class GoToPageCommand implements Command {

    private final String pagePath;

    protected GoToPageCommand(String pagePath) {
        this.pagePath = pagePath;
    }

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        return CommandResult.forward(pagePath);
    }
}
