package com.soft.app.controller;

import com.soft.app.repository.custom.vcc.iot.IotDeviceRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotMachineRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/settings")
public class SettingController {
    @Autowired
    private IotMachineRepositoryCustom iotMachineRepositoryCustom;

    @Autowired
    IotDeviceRepositoryCustom iotDeviceRepositoryCustom;

    @GetMapping("warning")
    private String settingRate(ModelMap model) {
        model.addAttribute("iotMachine", iotMachineRepositoryCustom.findByOuth());
        return "settings/warning";
    }

    @RequestMapping(value = {"/machine"}, method = RequestMethod.GET)
    public String machineSetting(ModelMap model) {
        model.addAttribute("iotMachine",iotMachineRepositoryCustom.findByOuth());
        model.addAttribute("iotDevice",iotDeviceRepositoryCustom.findByOuth());
        //FIRST PAGE
        return "settings/machine";
    }

    @GetMapping("combine")
    public String settingComine(ModelMap model){
        return "settings/combine";
    }
}
