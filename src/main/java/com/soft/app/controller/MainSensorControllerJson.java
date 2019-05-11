package com.soft.app.controller;

import com.soft.app.entity.vcc.device.MainSensor;
import com.soft.app.repository.custom.vcc.device.MainSensorRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mainsensors")
public class MainSensorControllerJson {
    @Autowired
    MainSensorRepositoryCustom mainSensorRepositoryCustom;

    @GetMapping("deviceCode")
    public MainSensor findLastRecordByDeviceCodeOuth(
            @RequestParam(value = "deviceCode") String deviceCode) {
        return mainSensorRepositoryCustom.findLastRecordByDeviceCodeOuth(deviceCode);
    }
}
