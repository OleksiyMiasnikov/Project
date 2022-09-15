package com.myProject.service.command;

import com.myProject.dao.entitie.Goods;
import com.myProject.dao.entitie.Role;
import com.myProject.exception.DaoException;
import com.myProject.service.GoodsManager;
import com.myProject.service.RoleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class NewGoods implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(NewGoods.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("--- NewGoods ---");
        req.getRequestDispatcher("jsp/goodsDetails.jsp").forward(req, resp);

    }
}
