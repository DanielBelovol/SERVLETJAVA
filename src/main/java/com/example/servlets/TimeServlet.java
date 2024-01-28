package com.example.servlets;

import com.example.Date;
import com.example.MyTemplateEngine;
import com.example.servlets.service.CookieService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private static final String patternOfTime = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine myTemplateEngine = MyTemplateEngine.getTemplateEngine();
        Date date = new Date();

        resp.setContentType("text/html;charset=UTF-8");

        String timeZone = getUtcTimeZone(req);

        String parsedTimeZone = timeZone.replace("+", "");
        int timeZoneInt = Integer.parseInt(parsedTimeZone.substring(3).trim());

        resp.addCookie(new Cookie("UTC", String.valueOf(timeZoneInt)));

        String formattedTime = date.getDate(timeZoneInt, patternOfTime);

        Context context = new Context();
        context.setVariable("timeZone", timeZoneInt);
        context.setVariable("formattedTime", formattedTime);

        try (PrintWriter writer = resp.getWriter()) {
            myTemplateEngine.process("timeTemplate", context, writer);
        }
    }



    private String getUtcTimeZone(HttpServletRequest req) {
        CookieService cookieService = new CookieService();

        String timeZone = req.getParameter("timezone");

        if (timeZone == null) {
            timeZone = cookieService.getCookieValue(req, "UTC");
            if (timeZone == null) {
                timeZone = "UTC+0";
            }
        }
        return timeZone;
    }
}