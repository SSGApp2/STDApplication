package com.soft.app.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("footprints")
public class FootprintController {
    private static final Logger LOGGER = LogManager.getLogger(FootprintController.class);

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String home(ModelMap model) {
//        model.addAttribute("iotFootprints", );
//        model.addAttribute("iotMachines", );
        //FIRST PAGE
        return "footprint/create";
    }
}
