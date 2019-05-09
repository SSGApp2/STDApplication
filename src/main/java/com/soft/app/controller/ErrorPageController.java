package com.soft.app.controller;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
public class ErrorPageController implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        String page="errorPage/default";
        if (status.value() == HttpStatus.NOT_FOUND.value()) {
            page= "errorPage/error404";
        } else if (status.value() == HttpStatus.FORBIDDEN.value()) {
            page= "errorPage/error403";
        } else if (status.value() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            page="errorPage/error500";
        }
        return new ModelAndView(page);
    }
}
