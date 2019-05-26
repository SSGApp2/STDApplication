package com.soft.app.controller;

import com.jfilter.filter.FieldFilterSetting;
import com.jfilter.filter.FilterBehaviour;
import com.soft.app.entity.vcc.iot.IotDevice;
import com.soft.app.entity.vcc.iot.IotFootprint;
import com.soft.app.entity.vcc.iot.IotFootprintMachine;
import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.repository.custom.IotFootprintMachineRepositoryCustom;
import com.soft.app.repository.custom.vcc.iot.IotFootprintRepositoryCustom;
import com.soft.app.service.iot.IotFootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/iotfootprints")
public class IotFootprintControllerJson {

    @Autowired
    private IotFootprintRepositoryCustom iotFootprintRepositoryCustom;
    @Autowired
    private IotFootprintService iotFootprintService;
    @Autowired
    private IotFootprintMachineRepositoryCustom iotFootprintMachineRepositoryCustom;

    @FieldFilterSetting(className = IotFootprint.class, fields = {"iotFootPrintMachines"})
    @RequestMapping(value = "findByOuth", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<IotFootprint> findByOuth() {
        return iotFootprintRepositoryCustom.findByOuth();
    }

    @PostMapping()
    public void create(@RequestBody String json) {
        iotFootprintService.createOrUpdate(json);
    }


    @FieldFilterSetting(className = IotMachine.class, fields = {"macName", "id", "deviceName", "iotDevice"}, behaviour = FilterBehaviour.KEEP_FIELDS)
    @FieldFilterSetting(className = IotDevice.class, fields = {"deviceName"}, behaviour = FilterBehaviour.KEEP_FIELDS)
    @RequestMapping(value = "findByIotFootprint/{id}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<IotFootprintMachine> findByIotFootprint(@PathVariable(value = "id") Long id) {
        return iotFootprintMachineRepositoryCustom.findByIotFootprintOuth(id);
    }

}
