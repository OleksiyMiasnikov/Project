package com.myProject.service.command;

import com.myProject.employee.Employee;
import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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


        final String username = "oleksiymiasnikov@gmail.com";
        final String password = "CashRegisterEPAM2022";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("oleksiymiasnikov@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("oleksiymiasnikov@gmail.com")
            );
            message.setSubject("Testing Gmail TLS");
            message.setText("Dear Mail Crawler,"
                    + "\n\n Please do not spam my email!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }




        /*Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
        Session session = Session.getInstance(prop, null);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("alex_1@ukr.net")); //employee.getUser().getEmail()
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("alex_1@ukr.net"));
            message.setSubject("report");
            message.setText("This is the email body");
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File("c:\\1.txt"));
        } catch (MessagingException e) {
            throw new AppException(e);
        }
*/
        return req.getHeader("referer");
    }
}
