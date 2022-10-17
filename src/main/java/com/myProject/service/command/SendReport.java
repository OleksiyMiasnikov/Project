package com.myProject.service.command;

import com.myProject.employee.Employee;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Implementation of SEND_REPORT_COMMAND
 */
public class SendReport implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(SendReport.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws DaoException, ServletException, IOException {
        logger.info("SEND_REPORT_COMMAND executed");
        Employee employee = (Employee) req.getSession().getAttribute("Employee");

        Properties properties = new Properties();
        Session session = Session.getInstance(properties, null);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("alex_1@ukr.net")); //employee.getUser().getEmail()
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("alex_1@ukr.net"));
            message.setSubject("report");
            message.setText("This is the email body");
        } catch (MessagingException e) {
            throw new AppException(e);
        }

        return req.getHeader("referer");
    }
}
