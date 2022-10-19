package com.myProject.service.command;

import com.myProject.service.exception.AppException;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Implementation of SEND_REPORT_COMMAND
 */
public class SendReport1 implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(SendReport1.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("SEND_REPORT_COMMAND executed");
        String filePDF = req.getServletContext().getRealPath((String) req.getSession().getAttribute("pdf"));
        final String username = "oleksiymiasnikov@gmail.com";
        final String password = "fasgvwhmekiknkam";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("oleksiymiasnikov@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("oleksiymiasnikov@gmail.com"));
            message.setSubject("Report");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Mail Body");
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePDF));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Done");
        } catch (MessagingException | IOException e) {
            throw new AppException("Unable to send email", e);
        }
        return req.getHeader("referer");
    }
}
