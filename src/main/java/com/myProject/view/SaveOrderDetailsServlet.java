package com.myProject.view;

import com.myProject.dao.entitie.OrderDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/saveOrderDetails")
public class SaveOrderDetailsServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(AdminFragmentServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doPost");
        resp.sendRedirect("saveOrderDetails");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doGet");
        OrderDetails orderDetails = new OrderDetails();
        String newGoods = req.getParameter("newGoods");
        String newId = req.getParameter("newId");
        logger.info(newId);

        //orderDetails.setOrder(req.getParameter();
        //String = req.getParameter();
        //String = req.getParameter();
        //String = req.getParameter();
    }
}
