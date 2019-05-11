package com.soft.app.controller;

import com.soft.app.repository.custom.vcc.iot.IotFootprintRepositoryCustom;
import com.soft.app.repository.vcc.iot.IotSensorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("dashboard")
public class DashboardController {
    private static final Logger LOGGER = LogManager.getLogger(DashboardController.class);

    @Autowired
    private IotFootprintRepositoryCustom iotFootprintRepositoryCustom;



    @RequestMapping(value = {"","/", "/index"}, method = RequestMethod.GET)
    public String homePage(ModelMap model) {
        model.addAttribute("iotFootprints",iotFootprintRepositoryCustom.findByOuth());
        //FIRST PAGE
        return "dashboard/index";
    }

    @GetMapping("main")
    private String mainDashboard(){
        return "dashboard/mainDashboard";
    }


}
