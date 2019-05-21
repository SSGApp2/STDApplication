package com.soft.app.controller;

import com.soft.app.service.MainSensorViewService;
import com.soft.app.spring.security.AuthorizeUtil;
import com.soft.app.util.BeanUtils;
import com.soft.app.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/mainsensorviews")
public class MainSensorViewControllerJson {

    @Autowired
    MainSensorViewService mainSensorViewService;

    @Autowired
    AuthorizeUtil authorizeUtil;

    @GetMapping("/example")
    public ResponseEntity<String> example() {
        return mainSensorViewService.example();
    }

    @GetMapping("/example")
    public ResponseEntity<String> getDataByDateFromTo(
            @RequestParam(value = "deviceCode") String deviceCode,
            @RequestParam(value = "sensorCode") String sensorCode,
            @RequestParam(value = "dateFrom", required = false) String dateFrom,
            @RequestParam(value = "dateTo", required = false) String dateTo
    ) {
        Map map = new HashMap();
        map.put("ouCode", authorizeUtil.getOuCode());
        map.put("dateFrom",dateFrom);
        map.put("dateTo",dateTo);
        map.put("deviceCode",deviceCode);
        map.put("sensorCode",sensorCode);
        return mainSensorViewService.getDataByDateFromTo(BeanUtils.toStringJson(map));
    }


}
