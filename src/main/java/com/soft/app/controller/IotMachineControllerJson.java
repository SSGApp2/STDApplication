package com.soft.app.controller;

import com.jfilter.filter.FieldFilterSetting;
import com.jfilter.filter.FilterBehaviour;
import com.soft.app.entity.vcc.iot.IotDevice;
import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.entity.vcc.iot.IotSensor;
import com.soft.app.repository.custom.vcc.iot.IotMachineRepositoryCustom;
import com.soft.app.repository.vcc.iot.IotDeviceRepository;
import com.soft.app.repository.vcc.iot.IotMachineRepository;
import com.soft.app.repository.vcc.iot.IotSensorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        iotDevice.setIsUsed("Y");
        iotDeviceRepository.save(iotDevice);
        IotMachine iotMachine = new IotMachine();
        iotMachine.setIotDevice(iotDevice);
        iotMachine.setMacName(iotm.getMacName());
        iotMachine.setLineToken(iotm.getLineToken());
        iotMachine.setDescription(iotm.getDescription());
//        iotMachine.setMacCode(iotm.getMacCode());
        return iotMachineRepository.save(iotMachine);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<IotMachine> updateIotMachine(@PathVariable("id") long id, @RequestBody IotMachine iotm) {
        IotMachine iotMachine = iotMachineRepository.findById(id).get();
        Long idDeviceOld = iotMachine.getIotDevice().getId();
        Optional<IotDevice> iotDeviceOld = iotDeviceRepository.findById(idDeviceOld);
        iotDeviceOld.map(row0 -> {

            row0.setIsUsed("N");
            iotDeviceRepository.save(row0);
            IotDevice iotDeviceNew = iotDeviceRepository.findById(iotm.getId()).get();
            iotDeviceNew.setIsUsed("Y");
            iotDeviceRepository.save(iotDeviceNew);

            return iotMachineRepository.findById(id)
                    .map(record -> {
                        record.setMacName(iotm.getMacName());
                        record.setLineToken(iotm.getLineToken());
                        record.setDescription(iotm.getDescription());
//                    record.setMacCode(iotm.getMacCode());
                        record.setIotDevice(iotDeviceNew);
                        IotMachine updated = iotMachineRepository.save(record);
                        return ResponseEntity.ok().body(updated);
                    }).orElse(ResponseEntity.notFound().build());

        }).orElse(ResponseEntity.notFound().build());

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> deleteIotMachine(@PathVariable("id") long id) {
        return iotMachineRepository.findById(id)
                .map(record -> {
                    IotDevice iotDevice = iotDeviceRepository.findById(record.getIotDevice().getId()).get();
                    iotDevice.setIsUsed("N");
                    iotDeviceRepository.save(iotDevice);

                    iotMachineRepository.deleteById(id);
                    iotSensorRepository.findByIotMachineId(id).forEach(row -> {
                        iotSensorRepository.deleteById(row.getId());
                    });
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @FieldFilterSetting(className = IotMachine.class, fields = {"macName", "id", "deviceName", "iotDevice"}, behaviour = FilterBehaviour.KEEP_FIELDS)
    @FieldFilterSetting(className = IotDevice.class, fields = {"deviceName"}, behaviour = FilterBehaviour.KEEP_FIELDS)
    @RequestMapping(value = "findByNotInFootprintOuth", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<IotMachine> findByNotInFootprintOuth() {
        return iotMachineRepositoryCustom.findByNotInFootprintOuth();
    }



}

