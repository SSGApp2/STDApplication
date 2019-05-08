package com.soft.app.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	static Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    private String loginPageUrl;

    private boolean returnParameterEnabled;

    private String returnParameterName;

    
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException reason) throws IOException, ServletException {

        if(null == loginPageUrl || "".equals(loginPageUrl))
            throw new RuntimeException("loginPageUrl has not been defined");

        String redirectUrl = loginPageUrl;

        if(isReturnParameterEnabled()) {
            String redirectUrlReturnParameterName = getReturnParameterName();

            if(null == redirectUrlReturnParameterName || "".equals(redirectUrlReturnParameterName))
                throw new RuntimeException("redirectUrlReturnParameterName has not been defined");

            String queryString = "";
            if(request.getQueryString() != null){
                queryString = "?"+request.getQueryString();
            }

            if (!request.getServletPath().contains("resource")){
                request.getSession().setAttribute("redirect_url",request.getServletPath()+queryString);
            }
        }

        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, redirectUrl);

        return;
    }

    public String getLoginPageUrl() {
        return loginPageUrl;
    }

    public void setLoginPageUrl(String loginPageUrl) {
        this.loginPageUrl = loginPageUrl;
    }

    public boolean isReturnParameterEnabled() {
        return returnParameterEnabled;
    }

    public void setReturnParameterEnabled(boolean returnParameterEnabled) {
        this.returnParameterEnabled = returnParameterEnabled;
    }

    public String getReturnParameterName() {
        return returnParameterName;
    }

    public void setReturnParameterName(String returnParameterName) {
        this.returnParameterName = returnParameterName;
    }
}
