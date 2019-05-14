package com.soft.app.controller;

import com.soft.app.entity.vcc.iot.IotDevice;
import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.entity.vcc.iot.IotSensor;
import com.soft.app.repository.vcc.iot.IotDeviceRepository;
import com.soft.app.repository.vcc.iot.IotMachineRepository;
import com.soft.app.repository.vcc.iot.IotSensorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.repository.custom.vcc.iot.IotMachineRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("api/iotmachines")
public class IotMachineControllerJson {
    private static final Logger LOGGER = LogManager.getLogger(IotMachineControllerJson.class);

    @Autowired
    IotMachineRepository iotMachineRepository;

    @Autowired
    IotDeviceRepository iotDeviceRepository;

    @Autowired
    IotMachineRepositoryCustom iotMachineRepositoryCustom;

    @Autowired
    IotSensorRepository iotSensorRepository;

    @PostMapping("/createIotMachine")
    public IotMachine createIotMachine(@RequestBody IotMachine iotm) {
        IotDevice iotDevice = iotDeviceRepository.findById(iotm.getId()).get();
        IotMachine iotMachine = new IotMachine();
        iotMachine.setIotDevice(iotDevice);
        iotMachine.setMacName(iotm.getMacName());
//        iotMachine.setMacCode(iotm.getMacCode());
        return  iotMachineRepository.save(iotMachine);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<IotMachine> updateIotMachine(@PathVariable("id") long id,@RequestBody IotMachine iotm){
        IotDevice iotDevice = iotDeviceRepository.findById(iotm.getId()).get();
        return iotMachineRepository.findById(id)
                .map(record -> {
                    record.setMacName(iotm.getMacName());
//                    record.setMacCode(iotm.getMacCode());
                    record.setIotDevice(iotDevice);
                    IotMachine updated = iotMachineRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> deleteIotMachine(@PathVariable("id") long id) {
        return iotMachineRepository.findById(id)
                .map(record -> {
                    iotMachineRepository.deleteById(id);
                    iotSensorRepository.findByIotMachineId(id).forEach(row ->{
                        iotSensorRepository.deleteById(row.getId());
                    });
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("findByNotInFootprintOuth")
    public List<IotMachine> findByNotInFootprintOuth() {
        return iotMachineRepositoryCustom.findByNotInFootprintOuth();

    }

}

