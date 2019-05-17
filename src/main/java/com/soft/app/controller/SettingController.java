package com.soft.app.controller;

import com.soft.app.repository.custom.vcc.iot.IotMachineRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public class SettingController {
    @Autowired
    private IotMachineRepositoryCustom iotMachineRepositoryCustom;

    @GetMapping("warning")
    private String settingRate(ModelMap model) {
        model.addAttribute("iotMachine", iotMachineRepositoryCustom.findByOuth());
        return "settings/warning";
    }
}