package com.myProject.service.command;

import com.myProject.service.exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChangeLocale implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        String lang = req.getParameter("lang");
        req.getSession().setAttribute("locale", lang);
        String previousPage = req.getHeader("referer");
        int last = previousPage.lastIndexOf('/');
        String result = previousPage.substring(last + 1);
        if ("".equals(result)) result = "index.jsp";
        return result;
    }
}
