package com.soft.app.controller;

import com.soft.app.entity.vcc.iot.IotSensorCombine;
import com.soft.app.entity.vcc.iot.IotSensorCombineDetail;
import com.soft.app.entity.vcc.iot.custom.IotSensorCombineCustom;
import com.soft.app.repository.custom.vcc.iot.IotDeviceRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotSensorCombineRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotSensorRepositoryCustom;
import com.soft.app.repository.vcc.iot.IotDeviceRepository;
import com.soft.app.repository.vcc.iot.IotSensorCombineDetailRepository;
import com.soft.app.repository.vcc.iot.IotSensorCombineRepository;
import com.soft.app.repository.vcc.iot.IotSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/iotsensorcombines")
public class IotSensorCombineControllerJson {

    @Autowired
    IotDeviceRepositoryCustom iotDeviceRepositoryCustom;

    @Autowired
    IotSensorRepositoryCustom iotSensorRepositoryCustom;

    @Autowired
    IotDeviceRepository iotDeviceRepository;

    @Autowired
    IotSensorCombineRepository iotSensorCombineRepository;

    @Autowired
    IotSensorCombineDetailRepository iotSensorCombineDetailRepository;

    @Autowired
    IotSensorRepository iotSensorRepository;

    @Autowired
    IotSensorCombineRepositoryCustom iotSensorCombineRepositoryCustom;


    @PostMapping("saveIotSensorCombine")
    @Transactional
    public ResponseEntity<List<IotSensorCombine>> saveIotSensorCombine(@RequestBody IotSensorCombineCustom iotSensorCombine){

        IotSensorCombine iotSensorCombine1 = new IotSensorCombine();
        iotSensorCombine1.setAlertMessage(iotSensorCombine.getAlertMessage());
        iotSensorCombineRepository.save(iotSensorCombine1);

        List<IotSensorCombineDetail> iotSensorCombineDetails = iotSensorCombine.getIotSensorCombineDetails();
        if(!iotSensorCombineDetails.isEmpty()) {
            iotSensorCombineDetails.forEach(row -> {
                row.setIotSensor(iotSensorRepository.findById(row.getIotSensor().getId()).get());
                row.setIotSensorCombine(iotSensorCombine1);
                iotSensorCombineDetailRepository.save(row);
            });
        }

        if(iotSensorCombine1.getId() == null){
            return ResponseEntity.notFound().build();
        }else
        {
//            List<IotSensorCombine> iotSensorCombine2 = iotSensorCombineRepositoryCustom.findById(iotSensorCombine1.getId());
            return ResponseEntity.ok().body(null);
        }
    }
}
