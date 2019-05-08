package com.soft.app.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class WebController {
    private static final Logger LOGGER = LogManager.getLogger(WebController.class);
    @GetMapping("welcome")
    public String welcome() {
        return "welcome";
    }

}
