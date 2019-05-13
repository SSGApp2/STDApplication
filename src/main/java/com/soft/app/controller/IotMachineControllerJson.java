package com.soft.app.controller;

import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.repository.custom.vcc.iot.IotMachineRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/iotmachines")
public class IotMachineControllerJson {
    @Autowired
    private IotMachineRepositoryCustom iotMachineRepositoryCustom;

    @GetMapping("findByNotInFootprintOuth")
    public List<IotMachine> findByNotInFootprintOuth() {
        return iotMachineRepositoryCustom.findByNotInFootprintOuth();
    }

}
