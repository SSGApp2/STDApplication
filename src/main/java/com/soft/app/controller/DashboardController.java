package com.soft.app.controller;

import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.entity.vcc.iot.IotSensor;
import com.soft.app.repository.custom.vcc.iot.IotMachineRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotSensorRepositoryCustom;
import com.soft.app.util.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dashboard")
public class DashboardController {
    private static final Logger LOGGER = LogManager.getLogger(DashboardController.class);

    @Autowired
    private IotMachineRepositoryCustom iotMachineRepositoryCustom;

    @Autowired
    private IotSensorRepositoryCustom iotSensorRepositoryCustom;


    @GetMapping("main/{id}")
    private String mainDashboard(ModelMap model, @PathVariable(value = "id") Long id) {
        IotMachine iotMachine = iotMachineRepositoryCustom.findByIdOuth(id);
        if (BeanUtils.isNull(iotMachine)) {
            return "errorPage/error404";
        }
        model.put("macName", iotMachine.getMacName());
        model.put("deviceCode", iotMachine.getIotDevice().getDeviceCode());
        model.put("iotSensors", iotMachine.getIotSensor());
        return "dashboard/mainDashboard";
    }


    @GetMapping("device/{id}")
    private String device(ModelMap model, @PathVariable(value = "id") Long id) {
        IotMachine iotMachine = iotMachineRepositoryCustom.findByIdOuth(id);
        if (BeanUtils.isNull(iotMachine)) {
            return "errorPage/error404";
        }
        model.addAttribute("iotMachine", iotMachine);
        return "dashboard/device";
    }

    @GetMapping("sensorHistory/{id}")
    private String sensorHistory(ModelMap model, @PathVariable(value = "id") Long id) {
        IotSensor iotSensor = iotSensorRepositoryCustom.findByIdOth(id);
        if (BeanUtils.isNull(iotSensor)) {
            return "errorPage/error404";
        }
        model.addAttribute("IotSensor", iotSensor);
        return "dashboard/sensorHistory";
    }


}
