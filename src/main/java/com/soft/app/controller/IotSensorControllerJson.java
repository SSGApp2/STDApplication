package com.soft.app.controller;

import com.soft.app.entity.vcc.iot.IotSensor;
import com.soft.app.repository.custom.vcc.iot.IotSensorRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/iotsensors")
public class IotSensorControllerJson {
    @Autowired
    private IotSensorRepositoryCustom iotSensorRepositoryCustom;

    @GetMapping("findByDeviceCodeOth")
    public List<IotSensor> findByDeviceCodeOth(
            @RequestParam(value = "deviceCode") String deviceCode
    ) {
        return iotSensorRepositoryCustom.findByIotDeviceCodeOth(deviceCode);
    }
}
