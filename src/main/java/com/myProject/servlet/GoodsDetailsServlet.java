package com.myProject.servlet;

import com.myProject.dao.entitie.Goods;
import com.myProject.dao.entitie.Role;
import com.myProject.dao.entitie.User;
import com.myProject.exception.DaoException;
import com.myProject.service.GoodsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/serveGoods")
public class GoodsDetailsServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(GoodsDetailsServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if ("Cancel".equals(req.getParameter("button"))) {
            try {
                logger.info("'Cansel' has pressed");
                resp.sendRedirect("main");
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        long id = 0L;
        double price = 0;
        String name = req.getParameter("newName");
        String unit = req.getParameter("newUnit");

        String strPrice = req.getParameter("newPrice");
        if (strPrice != null && !strPrice.equals("")) {
            price = Double.parseDouble(strPrice);
        }

        String strId = req.getParameter("id");
        if (strId != null && !strId.equals("")) {
            id = Long.parseLong(strId);
        }

        try {
            GoodsManager goodsManager =
                    (GoodsManager) req.getSession()
                    .getServletContext()
                    .getAttribute("GoodsManager");

            Goods newGoods = new Goods(id, name, unit, price);
            if (id == 0L) {
                if (goodsManager.create(newGoods) != null){
                    logger.info(name + " added");
                } else {
                    logger.info("Unable to add " + name);
                }
            } else {
                goodsManager.update(newGoods);
                logger.info(name + " updated");
            }
            List<Goods> goodsList = goodsManager.findAllGoods();
            req.setAttribute("result", goodsList);
            req.setAttribute("Window", "jsp/commodityExpertWindow.jsp");
            req.getRequestDispatcher("main").forward(req, resp);
        } catch (IOException | DaoException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
