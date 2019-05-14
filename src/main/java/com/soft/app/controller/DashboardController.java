package com.soft.app.controller;

import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.entity.vcc.iot.IotSensor;
import com.soft.app.repository.custom.vcc.iot.IotMachineRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotSensorRepositoryCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("dashboard")
public class DashboardController {
    private static final Logger LOGGER = LogManager.getLogger(DashboardController.class);

    @Autowired
    private IotMachineRepositoryCustom iotMachineRepositoryCustom;

    @Autowired
    private IotSensorRepositoryCustom iotSensorRepositoryCustom;


    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String homePage(ModelMap model) {
//        model.addAttribute("iotFootprints", );
        model.addAttribute("iotMachines", iotMachineRepositoryCustom.findByOuth());
        //FIRST PAGE
        return "dashboard/index";
    }

    @GetMapping("main/{id}")
    private String mainDashboard(ModelMap model, @PathVariable(value = "id") Long id) {
        IotMachine iotMachine = iotMachineRepositoryCustom.findByIdOuth(id);
        model.put("macName", iotMachine.getMacName());
        model.put("iotSensors", iotMachine.getIotSensor());
        return "dashboard/mainDashboard";
    }

    @GetMapping("setting")
    private String settingRate(ModelMap model) {
        model.addAttribute("iotMachine", iotMachineRepositoryCustom.findByOuth());
        return "dashboard/setting";
    }

    @GetMapping("device")
    private String device(ModelMap model) {
        model.addAttribute("iotMachine", iotMachineRepositoryCustom.findByOuth());
        return "dashboard/device";
    }


}
