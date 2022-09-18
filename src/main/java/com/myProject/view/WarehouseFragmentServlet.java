package com.myProject.view;

import com.myProject.dao.entitie.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/WarehouseFragment")
public class WarehouseFragmentServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(WarehouseFragmentServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("service start");
        List<Warehouse> warehouseList;
        warehouseList = (List<Warehouse>) req.getAttribute("result");
        try (PrintWriter printWriter = resp.getWriter()){
            for (Warehouse element : warehouseList) {
                printWriter.write(String.valueOf(element.getId()));
                printWriter.write("--");
                printWriter.write(element.getGoods().getName());
                printWriter.write("--");
                printWriter.write(element.getGoods().getUnit());
                printWriter.write("--");
                printWriter.write(String.valueOf(element.getQuantity()));
                printWriter.write("--");
                printWriter.write(String.valueOf(element.getGoods().getPrice()));
                printWriter.write("<br>");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("service finish");
    }
}
