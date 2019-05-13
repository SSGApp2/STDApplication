package com.soft.app.controller;

import com.soft.app.repository.custom.vcc.iot.IotDeviceRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotMachineRepositoryCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IotMachineController {
    private static final Logger LOGGER = LogManager.getLogger(IotMachineController.class);

    @Autowired
    IotMachineRepositoryCustom iotMachineRepositoryCustom;

    @Autowired
    IotDeviceRepositoryCustom iotDeviceRepositoryCustom;

    @RequestMapping(value = {"/machineSetting"}, method = RequestMethod.GET)
    public String machineSetting(ModelMap model) {
        model.addAttribute("iotMachine",iotMachineRepositoryCustom.findByOuth());
        model.addAttribute("iotDevice",iotDeviceRepositoryCustom.findByOuth());
        //FIRST PAGE
        return "iotmachine/machineSetting";
    }
}
