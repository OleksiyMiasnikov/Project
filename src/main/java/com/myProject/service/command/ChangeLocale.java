package com.myProject.service.command;

import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Implementation of LOCALE_COMMAND
 */
public class ChangeLocale implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ChangeLocale.class);
    /**
     * changes locale, puts it into session attribute and backs control to previous page
     *
     * @return address of previous page
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("LOCALE_COMMAND executed");
        String lang = req.getParameter("lang");
        req.getSession().setAttribute("locale", lang);
        logger.info("Current locale: " + lang);
        return req.getHeader("referer");
    }
}
