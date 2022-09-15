package com.myProject.service.command;

import com.myProject.dao.entitie.Goods;
import com.myProject.exception.DaoException;
import com.myProject.service.GoodsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ListOfGoods implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ListOfGoods.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("Start execute command -ListOfGoods-");
        GoodsManager goodsManager = (GoodsManager) req.getSession().getServletContext().getAttribute("GoodsManager");
        List<Goods> goodsList = goodsManager.findAllGoods();
        req.setAttribute("result", goodsList);
        req.setAttribute("Window", "jsp/commodityExpertWindow.jsp");
        req.getRequestDispatcher("main").forward(req, resp);
    }
}
