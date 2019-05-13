package com.soft.app.controller;

import com.soft.app.spring.security.AuthorizeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {
    private static final Logger LOGGER = LogManager.getLogger(WebController.class);

    @Autowired
    AuthorizeUtil authorizeUtil;

    @GetMapping("welcome")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String homePage() {
        return "redirect:/dashboard";
    }
}
