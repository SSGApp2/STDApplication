package com.soft.app.spring.configuration;

import com.soft.app.spring.security.AuthorizeUtil;
import com.soft.app.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Autowired
    private AuthorizeUtil authorizeUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (BeanUtils.isNotNull(modelAndView) && BeanUtils.isNotNull(authorizeUtil.getOuCode())) {
            String ouCode = authorizeUtil.getOuCode();
            modelAndView.addObject("ouCode",ouCode);
        }

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

}

