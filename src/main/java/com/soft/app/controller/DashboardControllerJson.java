package com.soft.app.controller;

import com.soft.app.repository.custom.vcc.iot.IotFootprintRepositoryCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dashboard")
public class DashboardControllerJson {
    private static final Logger LOGGER = LogManager.getLogger(DashboardController.class);

    @Autowired
    private IotFootprintRepositoryCustom iotFootprintRepositoryCustom;



}
