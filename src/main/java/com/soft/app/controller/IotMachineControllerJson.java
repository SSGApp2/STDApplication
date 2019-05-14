package com.soft.app.controller;

import com.soft.app.entity.vcc.iot.IotDevice;
import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.repository.vcc.iot.IotDeviceRepository;
import com.soft.app.repository.vcc.iot.IotMachineRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.repository.custom.vcc.iot.IotMachineRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/iotmachine")
public class IotMachineControllerJson {
    private static final Logger LOGGER = LogManager.getLogger(IotMachineControllerJson.class);

    @Autowired
    IotMachineRepository iotMachineRepository;

    @Autowired
    IotDeviceRepository iotDeviceRepository;

    @Autowired
    IotMachineRepositoryCustom iotMachineRepositoryCustom;

    @PostMapping("/")
    public List<IotMachine> createIotMachine(@RequestBody IotMachine iotm) {
        IotDevice iotDevice = iotDeviceRepository.findById(1l).get();
        IotMachine iotMachine = new IotMachine();
        iotMachine.setIotDevice(iotDevice);
        iotMachine.setMacName("002");
        iotMachineRepository.save(iotMachine);
        return null;
    }


    @GetMapping("findByNotInFootprintOuth")
    public List<IotMachine> findByNotInFootprintOuth() {
        return iotMachineRepositoryCustom.findByNotInFootprintOuth();

    }

}

