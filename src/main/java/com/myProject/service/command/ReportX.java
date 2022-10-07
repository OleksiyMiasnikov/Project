package com.myProject.service.command;

import com.myProject.dto.Report;
import com.myProject.service.CashierManager;
import com.myProject.service.employee.Employee;
import com.myProject.service.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        Report report = manager.createReport();
        report.setSeniorCashier(employee.getUser().getLogin());
        String pathPDF = createPDF(report);
        req.getSession().setAttribute("report", report);
        employee.setMenuItems(new ArrayList<>(Arrays
                .asList(PRINT_REPORT_COMMAND,
                        SEND_REPORT_COMMAND,
                        BACK_COMMAND)));
        req.getSession().setAttribute("employee", employee);

        return PATH + "report_x.jsp";
    }

    private String createPDF(Report report) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        int pageHeight = (int) page.getTrimBox().getHeight();
        int pageWidth = (int) page.getTrimBox().getWidth();
        System.out.println("pageHeight: " + pageHeight + ", pageWidth: " + pageWidth);

        PDPageContentStream stream = new PDPageContentStream(document, page);
        stream.setLineWidth(1);

        int initX = 50;
        int initY = pageHeight-50;
        int cellHeight = 30;
        int cellWidth = 80;
        int columnCount = 6;
        int rowCount = 10;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                stream.addRect(initX, initY, cellWidth, -cellHeight);
                stream.beginText();
                stream.newLineAtOffset(initX + 10, initY - cellHeight + 10);
                stream.setFont(PDType1Font.TIMES_ROMAN, 14);
                stream.showText("Cell #" + (i + 1) + ":" + (j + 1));
                stream.endText();
                initX += cellWidth;
            }
            initX = 50;
            initY -= cellHeight;
        }
        stream.stroke();
        stream.close();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        document.save("d:\\" + dtf.format(now) + "_X_report.pdf");
        document.close();
        return null;
    }

}
