package com.example.servlets.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieService {
    public String getCookieValue(HttpServletRequest req, String nameOfValue) {
        Cookie[] cookies = req.getCookies();
        String utcValue = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("UTC".equals(cookie.getName())) {
                    utcValue = cookie.getValue();
                    break;
                }
            }
        } else {
            return null;
        }

        return "UTC" + utcValue;
    }
}
