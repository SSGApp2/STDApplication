package com.soft.app.controller;

import com.soft.app.entity.vcc.iot.IotSensor;
import com.soft.app.repository.custom.vcc.iot.IotFootprintRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotMachineRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotSensorRepositoryCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("dashboard")
public class DashboardController {
    private static final Logger LOGGER = LogManager.getLogger(DashboardController.class);

    @Autowired
    private IotFootprintRepositoryCustom iotFootprintRepositoryCustom;

    @Autowired
    private IotSensorRepositoryCustom iotSensorRepositoryCustom;

    @Autowired
    private IotMachineRepositoryCustom iotMachineRepositoryCustom;

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String homePage(ModelMap model) {
        model.addAttribute("iotFootprints", iotFootprintRepositoryCustom.findByOuth());
        //FIRST PAGE
        return "dashboard/index";
    }

    @GetMapping("main")
    private String mainDashboard(ModelMap model) {
        List<IotSensor> iotSensor = iotSensorRepositoryCustom.findByIotDeviceCodeOth("XDK002");
        model.put("iotSensors", iotSensor);
        return "dashboard/mainDashboard";
    }

    @GetMapping("setting")
    private String settingRate(ModelMap model) {
        model.addAttribute("iotMachine",iotMachineRepositoryCustom.findByOuth());
        return "dashboard/setting";
    }


}
