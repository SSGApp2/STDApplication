package com.soft.app.controller;

import com.soft.app.repository.custom.vcc.iot.IotDeviceRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotMachineRepositoryCustom;
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

    @Autowired
    IotMachineRepositoryCustom iotMachineRepositoryCustom;

    @Autowired
    IotDeviceRepositoryCustom iotDeviceRepositoryCustom;

    @GetMapping("welcome")
    public String welcome() {
        return "welcome";
    }


    @RequestMapping(value = {"/machineSetting"}, method = RequestMethod.GET)
    public String machineSetting(ModelMap model) {
        model.addAttribute("iotMachine",iotMachineRepositoryCustom.findByOuth());
        model.addAttribute("iotDevice",iotDeviceRepositoryCustom.findByOuth());
        //FIRST PAGE
        return "dashboard/machineSetting";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String homePage() {
        return "redirect:/dashboard";
    }
}
