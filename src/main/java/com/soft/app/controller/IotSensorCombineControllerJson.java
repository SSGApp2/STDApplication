package com.soft.app.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.entity.vcc.iot.IotSensorCombine;
import com.soft.app.entity.vcc.iot.IotSensorCombineDetail;
import com.soft.app.entity.vcc.iot.custom.IotSensorCombineCustom;
import com.soft.app.repository.custom.vcc.iot.IotDeviceRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotSensorCombineDetailRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotSensorCombineRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotSensorRepositoryCustom;
import com.soft.app.repository.vcc.iot.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("api/iotsensorcombines")
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

    @Autowired
    IotMachineRepository iotMachineRepository;

    @GetMapping("sensorcombinedetailbymachineid")
    public  List<Map> sensorCombineDetailByMachineId(@RequestParam(value = "id") Long id){
        return iotSensorCombineRepositoryCustom.findDetailAllByMachineOuth(id);
    }


    @GetMapping("finddetailallbyid")
    public  List<Map> findDetailAllByID(@RequestParam(value = "id") Long id){
        return iotSensorCombineRepositoryCustom.findDetailAllByID(id);
    }
    @PostMapping("saveIotSensorCombine")
    @Transactional
    public ResponseEntity<List<IotSensorCombine>> saveIotSensorCombine(@RequestBody IotSensorCombineCustom iotSensorCombine){
        IotMachine iotMachine = iotMachineRepository.findById(iotSensorCombine.getIotMachineId()).get();
        IotSensorCombine iotSensorCombine1 = new IotSensorCombine();
        iotSensorCombine1.setAlertMessage(iotSensorCombine.getAlertMessage());
        iotSensorCombine1.setRepeatAlert(iotSensorCombine.getRepeatAlert());
        iotSensorCombine1.setRepeatUnit(iotSensorCombine.getRepeatUnit());
        iotSensorCombine1.setAlertType(iotSensorCombine.getAlertType());
        iotSensorCombine1.setIsActive("Y");
        iotSensorCombine1.setIotMachine(iotMachine);
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

    @PostMapping("/updateSensorCombine")
    @Transactional
    public ResponseEntity<?> updateSensorCombine(
            @RequestBody IotSensorCombineCustom iotSensorCombine,
            @RequestParam(value = "id") Long id
           ){
       return iotSensorCombineRepository.findById(id).map(row -> {

           iotSensorCombine.getIotSensorCombineDetails().forEach(row1 -> {
               if(row1.getId() == 0){
                   row1.setIotSensor(iotSensorRepository.findById(row1.getIotSensor().getId()).get());
                    row1.setIotSensorCombine(iotSensorCombineRepository.findById(id).get());
                   iotSensorCombineDetailRepository.save(row1);
               }else{
                   IotSensorCombineDetail iotSensorCombineDetail
                           = iotSensorCombineDetailRepository.findById(row1.getId()).get();
                   iotSensorCombineDetail.setAmount(row1.getAmount());
                   iotSensorCombineDetail.setDisplayType(row1.getDisplayType());
                   iotSensorCombineDetail.setValueType(row1.getValueType());
                   iotSensorCombineDetail.setIotSensor(iotSensorRepository.findById(row1.getIotSensor().getId()).get());
                   iotSensorCombineDetailRepository.save(iotSensorCombineDetail);
               }
           });

           row.setAlertMessage(iotSensorCombine.getAlertMessage());
           row.setRepeatUnit(iotSensorCombine.getRepeatUnit());
           row.setRepeatAlert(iotSensorCombine.getRepeatAlert());
           row.setAlertType(iotSensorCombine.getAlertType());
           iotSensorCombineRepository.save(row);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping("deletesettingcombine")
    public void deleteSettingCombine(@RequestParam(value = "id") Long id){
        iotSensorCombineRepository.deleteById(id);
    }

    @PostMapping("deleteSensorCombineDetail")
    @Transactional
    public void deleteSensorCombineDetail(@RequestBody String dataid){

        JsonParser jsonParser = new JsonParser();
        JsonObject objectFromString = jsonParser.parse(dataid).getAsJsonObject();
       JsonArray jsonElements = objectFromString.getAsJsonArray("dataid");
       for (int i = 0; i < jsonElements.size(); i++){
            iotSensorCombineDetailRepository.deleteById(Long.parseLong(jsonElements.get(i).toString()));
       }
    }
}
