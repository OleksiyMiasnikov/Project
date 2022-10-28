package com.myProject.service.command;

import com.myProject.service.CommodityExpertManager;
import com.myProject.entitie.Warehouse;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.myProject.util.Constants.PATH;
import static com.myProject.util.Constants.REMAINS_COMMAND;
/**
 * Implementation of REMAINS_COMMAND
 */
public class Remains implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(Remains.class);
    /**
     * prepares data for jsp page
     *
     * @return address of 'warehouse_list.jsp'
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("REMAINS_COMMAND executed");
        HttpSession session = req.getSession();
        CommodityExpertManager manager =
                (CommodityExpertManager) req
                        .getServletContext()
                        .getAttribute("CommodityExpertManager");
        String strPage = req.getParameter("page");
        int currentPage = 1;
        if (strPage != null &&
            !"".equals(strPage) &&
            REMAINS_COMMAND.equals(session.getAttribute("command_name"))) {
            currentPage = Integer.parseInt(strPage);
        }
        int pagesTotal = (int)Math. ceil(manager.findRowsTotalInWarehouse()/10d);
        List<Warehouse> list = manager.findAll((currentPage - 1) * 10, 10);
        Map<String, Double> totals = manager.WarehouseTotals();
        session.setAttribute("result", list);
        session.setAttribute("page", currentPage);
        session.setAttribute("pages_total", pagesTotal);
        session.setAttribute("command_name", REMAINS_COMMAND);
        session.setAttribute("total_amount", totals.get("total_amount"));
        session.setAttribute("total_quantity", totals.get("total_quantity"));
        session.setAttribute("title", "command.remains");
        return PATH + "warehouse_list.jsp";
    }
}
