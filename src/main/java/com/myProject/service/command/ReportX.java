package com.myProject.service.command;

import com.myProject.dto.Report;
import com.myProject.dto.ReportItem;
import com.myProject.service.CashierManager;
import com.myProject.service.employee.Employee;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static com.myProject.util.Constants.*;

public class ReportX implements Command {
    private static final Logger logger = (Logger) LogManager.getLogger(ReportX.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DaoException, ServletException, IOException {
        logger.info("Starting preparing X report");
        CashierManager manager =
                (CashierManager) req.getServletContext().getAttribute("CashierManager");
        Employee employee =
                (Employee) req.getSession().getAttribute("employee");

        String typeOfReport = "X_report";
        String title = "command.x_reports";
        if ("YES".equals(req.getParameter("z_report"))) {
            typeOfReport = "Z_report";
            title = "command.y_reports";
        }
        Report report = manager.createReport(typeOfReport);
        report.setSeniorCashier(employee.getUser().getLogin());
        String filePDF = createPDF(req, report);
        /*
        if (Desktop.isDesktopSupported()) {
            try {
               // File myFile = new File(filePDF);
                Desktop.getDesktop().open(new File(req.getServletContext().getRealPath(filePDF)));
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }*/
        req.getSession().setAttribute("pdf", filePDF);
        employee.setMenuItems(new ArrayList<>(Arrays
                .asList(PRINT_REPORT_COMMAND,
                        SEND_REPORT_COMMAND,
                        BACK_COMMAND)));
        req.getSession().setAttribute("employee", employee);
        req.getSession().setAttribute("title", title);
        System.out.printf(report.toString());
        //return PATH + "report_x2.jsp";
        return "ReportPDF";
    }

    private String createPDF(HttpServletRequest req, Report report) throws IOException {
        logger.info("start creating report in pdf");
        String file;
        try (PDDocument document = new PDDocument())
        {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream stream = new PDPageContentStream(document, page);

            String path = req.getServletContext().getRealPath("/WEB-INF/fonts/bahnschrift.ttf");

            PDFont font = PDType0Font.load(document,
                    new FileInputStream(path),
                    false);

            int pageHeight = (int) page.getTrimBox().getHeight();
            int xCoordinate = 50;
            int yCoordinate = pageHeight - 50;
            int rowHeight = 24;

            stream.beginText();
            stream.setFont(font, 14);
            stream.newLineAtOffset(xCoordinate, yCoordinate);
            stream.showText("*** Report X ***");
            stream.newLineAtOffset(0, -rowHeight);
            stream.showText("------------------------------------------------------------------------------");
            stream.newLineAtOffset(0, -rowHeight);
            stream.showText("Start date: " + report.getStartDate());
            stream.newLineAtOffset(0, -rowHeight);
            stream.showText("End date  : " + report.getEndDate());
            stream.newLineAtOffset(0, -rowHeight);
            stream.showText("Senior cashier: " + report.getSeniorCashier());
            stream.newLineAtOffset(0, -rowHeight);
            stream.showText("------------------------------------------------------------------------------");
            stream.setFont(font, 12);
            stream.newLineAtOffset(0, -rowHeight);
            stream.showText("Id");
            stream.newLineAtOffset(40, 0);
            stream.showText("Product name");
            stream.newLineAtOffset(200, 0);
            stream.showText("Unit");
            stream.newLineAtOffset(40, 0);
            stream.showText("Quantity");
            stream.newLineAtOffset(70, 0);
            stream.showText("Price");
            stream.newLineAtOffset(100, 0);
            stream.showText("Amount");
            stream.newLineAtOffset(-450, -rowHeight);
            stream.setFont(font, 14);
            stream.showText("------------------------------------------------------------------------------");
            stream.newLineAtOffset(0, -rowHeight);
            stream.setFont(font, 12);
            for (ReportItem element : report.getList()) {
                stream.showText(String.valueOf(element.getProductId()));
                stream.newLineAtOffset(40, 0);
                stream.showText(element.getProductName());
                stream.newLineAtOffset(200, 0);
                stream.showText(String.valueOf(element.getUnit().getLabel()));
                stream.newLineAtOffset(40, 0);
                stream.showText(String.valueOf(element.getQuantity()));
                stream.newLineAtOffset(70, 0);
                stream.showText(String.valueOf(element.getPrice()));
                stream.newLineAtOffset(100, 0);
                stream.showText(String.format("%-7.2f", element.getAmount()));
                stream.newLineAtOffset(-450, -rowHeight);
            }
            stream.setFont(font, 14);
            stream.showText("------------------------------------------------------------------------------");
            stream.newLineAtOffset(0, -rowHeight);
            stream.showText("Total quantity (kg): " + String.format("%-7.2f", report.getKgTotal()));
            stream.newLineAtOffset(0, -rowHeight);
            stream.showText("Total quantity (pcs): " + String.format("%-7d", report.getPcsTotal()));
            stream.newLineAtOffset(0, -rowHeight);
            stream.showText("Total amount: " + String.format("%-7.2f", report.getAmountTotal()));
            stream.newLineAtOffset(0, -rowHeight);
            stream.endText();
            stream.close();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy-HH_mm_ss");
            LocalDateTime now = LocalDateTime.now();
            file = "/out/" + dtf.format(now) + "-X_report.pdf";
            document.save(req.getServletContext().getRealPath(file));
        }
        logger.info("finish creating report in pdf");
        return file;
    }

}
