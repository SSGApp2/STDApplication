package com.soft.app.controller;

import com.soft.app.service.MainSensorViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/mainsensorviews")
public class MainSensorViewControllerJson {

    @Autowired
    MainSensorViewService mainSensorViewService;

    @GetMapping("/example")
    public ResponseEntity<String> example() {
        return mainSensorViewService.example();
    }
}
