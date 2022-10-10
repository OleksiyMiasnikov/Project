package com.myProject.service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

@WebServlet("/ReportPDF")
public class PdfDisplay extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pdfName = (String) req.getSession().getAttribute("pdf");
        File file = new File(req.getServletContext().getRealPath(pdfName));
        resp.setHeader("Content-Type", getServletContext().getMimeType(file.getName()));
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        resp.setHeader("Content-Disposition", "inline; filename=" + pdfName);
        OutputStream stream = resp.getOutputStream();
        Files.copy(file.toPath(), stream);
        stream.flush();
        stream.close();

    }
}
