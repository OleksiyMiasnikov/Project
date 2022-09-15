package com.myProject.service.command;

import com.myProject.dao.entitie.Goods;
import com.myProject.dao.entitie.User;
import com.myProject.exception.DbException;
import com.myProject.service.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListOfGoods implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ListOfGoods.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws DbException, ServletException, IOException {
        logger.info("Start execute command -ListOfGoods-");
        UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
        List<Goods> goodsList = userManager.findAllGoods();
        req.setAttribute("result", goodsList);
        logger.info("Finish execute command  -ListOfGoods-");
        req.setAttribute("Window", "Jsp/commodityExpertWindow.jsp");
        req.getRequestDispatcher("main").forward(req, resp);
    }
}
