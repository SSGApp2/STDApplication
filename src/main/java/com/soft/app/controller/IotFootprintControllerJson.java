package com.soft.app.controller;

import com.soft.app.entity.vcc.iot.IotFootprint;
import com.soft.app.repository.custom.vcc.iot.IotFootprintRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/iotfootprints")
public class IotFootprintControllerJson {

    @Autowired
    private IotFootprintRepositoryCustom iotFootprintRepositoryCustom;

    @GetMapping("findByOuth")
    public List<IotFootprint> findByOuth() {
        return iotFootprintRepositoryCustom.findByOuth();
    }
}
