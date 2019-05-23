package com.soft.app.controller;

import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.entity.vcc.iot.IotSensor;
import com.soft.app.repository.custom.vcc.iot.IotSensorRepositoryCustom;
import com.soft.app.repository.vcc.iot.IotMachineRepository;
import com.soft.app.repository.vcc.iot.IotSensorRepository;
import com.soft.app.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/iotsensors")
public class IotSensorControllerJson {
    @Autowired
    private IotSensorRepositoryCustom iotSensorRepositoryCustom;

    @Autowired
    private IotSensorRepository iotSensorRepository;

    @Autowired
    private IotMachineRepository iotMachineRepository;


    @PostMapping("saveOrUpdate")
    @Transactional
    public List<IotSensor> saveOrUpdate(@RequestBody List<IotSensor> iotSensors,
                                        @RequestParam(value = "id") Long id
    ) {
        IotMachine iotMachine = iotMachineRepository.findById(id).get();
        for (IotSensor iotSensor : iotSensors) {
            if (BeanUtils.isNotNull(iotSensor.getId())) { //update
                IotSensor iot = iotSensorRepository.findById(iotSensor.getId()).get();
                iotSensor.setIotMachine(iotMachine);
                iotSensor.setVersion(iot.getVersion());
                iotSensorRepository.save(iotSensor);
            } else {                                      //create
                iotMachine.addIotSensor(iotSensor);
                iotMachineRepository.save(iotMachine);
            }
        }
        return iotSensorRepository.findByIotMachineId(id);


    }

    @GetMapping("findIotSensorByMachineID")
    public List<IotSensor> findIotSensorByMachineID(@RequestParam(value = "id") Long id) {
        return iotSensorRepositoryCustom.findByMachineIdOth(id);
    }

    @GetMapping("findByMachineCodeAndSensorCodeOth")
    public IotSensor findByMachineCodeAndSensorCodeOth(@RequestParam(value = "sensorCode") String sensorCode) {
        return iotSensorRepositoryCustom.findByMachineCodeAndSensorCodeOth(sensorCode);
    }
}


