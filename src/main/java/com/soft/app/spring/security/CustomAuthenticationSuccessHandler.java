package com.soft.app.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	static Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
                                        throws ServletException, IOException {
    	
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        
        
        if (savedRequest == null) {
        	LOGGER.info("case 1");
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        String targetUrl = savedRequest.getRedirectUrl();
        LOGGER.info("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        
        if (!targetUrl.contains("resource") && !targetUrl.contains("spring_security") && !targetUrl.contains("login") && !targetUrl.contains("logout")){
        	getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }else{
        	getRedirectStrategy().sendRedirect(request, response, "/");
        }
    }
}
