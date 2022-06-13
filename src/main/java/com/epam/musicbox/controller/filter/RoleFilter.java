package com.epam.musicbox.controller.filter;


import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.util.Parameters;
import com.epam.musicbox.util.constant.Parameter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        HttpSession session = req.getSession();
        if (session.getAttribute(Parameter.ROLE) == null) {
            Role role;
            try {
                Jws<Claims> jws = AuthServiceImpl.getInstance().getJws(req);
                Claims body = jws.getBody();
                role = Parameters.getRole(body);
            } catch (ServiceException e) {
                role = Role.GUEST;
            }
            session.setAttribute(Parameter.ROLE, role.getValue());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
