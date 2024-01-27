package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String timeZone = Optional.ofNullable(req.getParameter("timezone")).orElse("UTC+0");
        String parsedTimeZone = timeZone.replace("+","");
        int timeZoneInt = Integer.parseInt(parsedTimeZone.substring(3).trim());


        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now(ZoneOffset.ofHours(timeZoneInt)).toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = now.format(formatter);

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.println("<!DOCTYPE html>");
            printWriter.println("<html>");
            printWriter.println("<head>");
            printWriter.println("<title>Servlet TimeServlet</title>");
            printWriter.println("</head>");
            printWriter.println("<body>");
            printWriter.println("<h1>Дата та час на даний момент (Timezone: " + timeZoneInt + "): " + formattedTime + "</h1>");
            printWriter.println("</body>");
            printWriter.println("</html>");
        }
    }
}