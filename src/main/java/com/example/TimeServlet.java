package com.example;

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
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine myTemplateEngine = MyTemplateEngine.getTemplateEngine();
        resp.setContentType("text/html;charset=UTF-8");

        String timeZone = req.getParameter("timezone");

        if (timeZone == null) {
            timeZone = getCookieValue(req, "UTC");
            if (timeZone == null) {
                timeZone = "UTC+0";
            }
        }

        String parsedTimeZone = timeZone.replace("+","");
        int timeZoneInt = Integer.parseInt(parsedTimeZone.substring(3).trim());

        resp.addCookie(new Cookie("UTC",String.valueOf(timeZoneInt)));

        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now(ZoneOffset.ofHours(timeZoneInt)).toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = now.format(formatter);

        Context context = new Context();
        context.setVariable("timeZone", timeZoneInt);
        context.setVariable("formattedTime", formattedTime);

        try (PrintWriter writer = resp.getWriter()) {
            myTemplateEngine.process("timeTemplate", context, writer);
        }
    }
    private String getCookieValue(HttpServletRequest req, String nameOfValue){
        Cookie[] cookies = req.getCookies();
        String utcValue = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("UTC".equals(cookie.getName())) {
                    utcValue = cookie.getValue();
                    break;
                }
            }
        }else {
            return null;
        }

        return "UTC"+utcValue;
    }
}