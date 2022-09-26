package com.myProject.view;

import com.myProject.dao.entitie.User;
import com.myProject.dao.entitie.Warehouse;
import com.myProject.exception.DaoException;
import com.myProject.service.UserManager;
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

import static com.myProject.util.Constants.*;

@WebServlet("/AdminFragment")
public class AdminFragmentServlet extends HttpServlet {
    private static final Logger logger = (Logger) LogManager.getLogger(AdminFragmentServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("service start");
        try (PrintWriter printWriter = resp.getWriter()){
            UserManager userManager = (UserManager) req.getSession().getServletContext().getAttribute("UserManager");
            List<User> userList = userManager.findAllUsers();
            printWriter.write("[AdminFragmentServlet: /AdminFragment]");
            printWriter.write("<br>");

            printWriter.write("<div class=\"table_header\">");
            printWriter.write("<button type=\"submit\" " +
                    "name=\"command\" " +
                    "value=\"" +
                    COMMAND_DELETE_USER +
                    "\"" +
                    "class=\"table_header\" " +
                    "style=\"width: 50px; color: black;\">" +
                    "<i class=\"fa-solid fa-trash-can\"></i>" +
                    "</button>");
            printWriter.write("<span class=\"table_header\" style=\"width: 50px;\">Id</span>");
            printWriter.write("<span class=\"table_header\" style=\"width: 200px;\">User login</span>");
            printWriter.write("<span class=\"table_header\" style=\"width: 200px;\">Role</span>");
            printWriter.write("</div>");
            printWriter.write("<br><hr>");
            for (User element : userList) {
                printWriter.write("<input type=\"checkbox\" style=\"width: 50px;text-align:center;\" " +
                        "name=\"users\" " +
                        "id=\"myCheck\" " +
                        "value=\"" +
                        element.getId() +
                        "\">");
                printWriter.write("<span class=\"item\" style=\"width: 50px;\" " +
                                    "value=" +
                                    element.getId() +
                                    ">");
                printWriter.write(String.valueOf(element.getId()));
                printWriter.write("</span>");
                printWriter.write("<span class=\"item\" style=\"width: 200px;text-align: center;\">");
                printWriter.write("<a " +
                                    "href=\"controller?command=" +
                                    COMMAND_UPDATE_USER +
                                    "&selectedUser=" +
                                    element.getId() +
                                    "\">");
                printWriter.write(element.getLogin());
                printWriter.write("</a>");
                printWriter.write("</span>");
                printWriter.write("<span class=\"item\" style=\"width: 200px;\">");
                printWriter.write(element.getRole().getName());
                printWriter.write("</span>");
                printWriter.write("<br><hr>");
            }
        } catch (IOException | DaoException e) {
            throw new RuntimeException(e);
        }
        logger.info("service finish");
    }
}
