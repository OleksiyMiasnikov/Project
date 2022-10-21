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
public class SendEmail implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(SendEmail.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, AppException {
        logger.info("SEND_REPORT_COMMAND executed");
        String filePDF = req.getServletContext().getRealPath((String) req.getSession().getAttribute("pdf"));
        String toRecipient = req.getParameter("to_address_mail");
        String subjectMail = req.getParameter("subject_mail");
        String bodyMail = req.getParameter("body_mail");
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
                    InternetAddress.parse(toRecipient));
            message.setSubject(subjectMail);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(bodyMail);
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePDF));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException | IOException e) {
            throw new AppException("Unable to send email", e);
        }
        return "controller?command=command.back";
    }
}
