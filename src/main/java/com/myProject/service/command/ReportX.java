package com.myProject.service.command;

import com.myProject.dto.Report;
import com.myProject.dto.ReportItem;
import com.myProject.service.CashierManager;
import com.myProject.employee.Employee;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.myProject.util.Constants.*;
/**
 * Implementation of
 */
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
        req.getSession().setAttribute("pdf", filePDF);
        employee.setMenuItems(List.of(PRINT_REPORT_COMMAND, SEND_REPORT_COMMAND, BACK_COMMAND));
        req.getSession().setAttribute("employee", employee);
        req.getSession().setAttribute("title", title);
        return PATH + "report_x.jsp";
    }

    private String createPDF(HttpServletRequest req, Report report) throws IOException {
        logger.info("start creating report in pdf");
        String file;
        try (PDDocument document = new PDDocument())
        {
            String path = req.getServletContext().getRealPath("/WEB-INF/fonts/bahnschrift.ttf");
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream stream = new PDPageContentStream(document, page);
            PDFont font = PDType0Font.load(document,
                    new FileInputStream(path),
                    false);
            int pageHeight = (int) page.getTrimBox().getHeight();
            int xCoordinate = 50;
            int yCoordinate = pageHeight - 50;
            int rowHeight = 24;

            stream.beginText();
            stream.setFont(font, 14);
            stream.setLeading(rowHeight);
            stream.newLineAtOffset(xCoordinate, yCoordinate);
            stream.showText("*** Report X ***");
            yCoordinate -= rowHeight;
            stream.newLine();
            stream.showText("------------------------------------------------------------------------------");
            yCoordinate -= rowHeight;
            stream.newLine();
            stream.showText("Start date: " + report.getStartDate());
            yCoordinate -= rowHeight;
            stream.newLine();
            stream.showText("End date  : " + report.getEndDate());
            yCoordinate -= rowHeight;
            stream.newLine();
            stream.showText("Senior cashier: " + report.getSeniorCashier());
            yCoordinate -= rowHeight;
            stream.newLine();
            stream.showText("------------------------------------------------------------------------------");
            stream.setFont(font, 12);
            yCoordinate -= rowHeight;
            stream.newLine();
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
            yCoordinate -= rowHeight;
            stream.newLineAtOffset(-450,  - rowHeight);
            stream.setFont(font, 14);
            stream.showText("------------------------------------------------------------------------------");
            yCoordinate -= rowHeight;
            stream.newLine();
            stream.setFont(font, 12);
            for (ReportItem element : report.getReportList()) {
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
                stream.newLineAtOffset(-450,  0);
                stream.newLine();
                yCoordinate -= rowHeight;
                if (yCoordinate < 50) {
                    stream.endText();
                    stream.close();
                    PDPage newPage = new PDPage();
                    document.addPage(newPage);
                    stream = new PDPageContentStream(document, newPage);
                    stream.beginText();
                    stream.setFont(font, 12);
                    yCoordinate = pageHeight - 50;
                    stream.newLineAtOffset(xCoordinate,  yCoordinate);
                    stream.setLeading(rowHeight);
                }
            }
            if (yCoordinate < 4 * rowHeight + 50) {
                stream.endText();
                stream.close();
                PDPage newPage = new PDPage();
                document.addPage(newPage);
                stream = new PDPageContentStream(document, newPage);
                stream.beginText();
                yCoordinate = pageHeight - 50;
                stream.newLineAtOffset(xCoordinate,  yCoordinate);
                stream.setLeading(rowHeight);
            }
            stream.setFont(font, 14);
            stream.showText("------------------------------------------------------------------------------");
            stream.newLine();
            stream.showText("Total quantity (kg): " + String.format("%-7.2f", report.getKgTotal()));
            stream.newLine();
            stream.showText("Total quantity (pcs): " + String.format("%-7d", report.getPcsTotal()));
            stream.newLine();
            stream.showText("Total amount: " + String.format("%-7.2f", report.getAmountTotal()));
            stream.newLine();
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
