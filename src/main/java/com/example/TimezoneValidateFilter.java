package com.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(value = "/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String param = servletRequest.getParameter("timezone");
        System.out.println(param);

        String regex = "UTC([ -]?(?:1[0-4]|0?[0-9])(:[0-5][0-9])?)";

        if (param == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (param.matches(regex)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Timezone parameter is invalid");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
