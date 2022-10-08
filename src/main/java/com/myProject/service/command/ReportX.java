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

        Report report = manager.createReport();
        report.setSeniorCashier(employee.getUser().getLogin());
        String filePDF = createPDF(req, report);
        req.getSession().setAttribute("pdf", filePDF);
        employee.setMenuItems(new ArrayList<>(Arrays
                .asList(PRINT_REPORT_COMMAND,
                        SEND_REPORT_COMMAND,
                        BACK_COMMAND)));
        req.getSession().setAttribute("employee", employee);

        return PATH + "report_x.jsp";
    }

    private String createPDF(HttpServletRequest req, Report report) throws IOException {
        logger.info("start PDF creating");
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        int pageHeight = (int) page.getTrimBox().getHeight();
        int pageWidth = (int) page.getTrimBox().getWidth();

        PDPageContentStream stream = new PDPageContentStream(document, page);
        stream.setLineWidth(1);

        int initX = 50;
        int initY = pageHeight-500;
        int cellHeight = 30;
        int cellWidth = 80;
        stream.beginText();
        PDFont font = PDType0Font.load(document, new FileInputStream("c:/windows/fonts/arial.ttf"), false);
        stream.setFont(font, 14);
        stream.setLeading(16.0f);
        stream.showText("*** Report X ***");
        stream.newLine();
        stream.showText("----------------------------------------------------------");
        stream.newLine();
        stream.showText("Start date: " + report.getStartDate());
        stream.newLine();
        stream.showText("End date  : " + report.getEndDate());
        stream.newLine();
        stream.showText("Senior cashier: " + report.getSeniorCashier());
        stream.newLine();
        stream.showText("----------------------------------------------------------");
        stream.newLine();
        stream.showText(" Id " + "|" + " Product name " + "|" + " Unit " + "|" + " Quantity " + "|" + " Price " + "|" + " Amount ");
        stream.newLine();
        stream.showText("----------------------------------------------------------");
        stream.newLine();
        stream.endText();
        for (ReportItem element : report.getList()) {
            stream.beginText();
            stream.showText(String.valueOf(element.getProductId()));
            stream.newLineAtOffset(initX + 50, initY);
            stream.showText(element.getProductName());
            stream.newLineAtOffset(initX + 200, initY);
            stream.showText(String.valueOf(element.getUnit()));
            stream.newLineAtOffset(initX + 50, initY);
            stream.showText(String.valueOf(element.getQuantity()));
            stream.newLineAtOffset(initX + 100, initY);
            stream.showText(String.valueOf(element.getPrice()));
            stream.newLineAtOffset(initX + 100, initY);
            stream.showText(String.valueOf(element.getAmount()));
            stream.endText();
            initY -= 30;
        }
        stream.beginText();
        stream.showText("----------------------------------------------------------");
        stream.newLine();
        stream.showText("Total quantity (kg): " + report.getKgTotal());
        stream.newLine();
        stream.showText("Total quantity (pcs): " + report.getPcsTotal());
        stream.newLine();
        stream.showText("Total amount: " + report.getAmountTotal());
        stream.newLine();
        stream.endText();
        stream.close();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String file = "/out/" + dtf.format(now) + "_X_report.pdf";
        document.save(req.getServletContext().getRealPath(file));
        document.close();
        return file;
    }

}
