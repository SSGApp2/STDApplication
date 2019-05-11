package com.soft.app.spring.configuration;


import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CacheControlFilter implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.setDateHeader("Last-Modified", new Date().getTime());
        resp.setHeader("Cache-Control", "public");
        resp.setHeader("Pragma", "cache");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
