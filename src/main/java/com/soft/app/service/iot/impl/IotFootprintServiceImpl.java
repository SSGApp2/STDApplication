package com.soft.app.service.iot.impl;

import com.google.gson.Gson;
import com.soft.app.entity.vcc.iot.IotFootprint;
import com.soft.app.entity.vcc.iot.IotFootprintMachine;
import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.repository.vcc.iot.IotFootprintMachineRepository;
import com.soft.app.repository.vcc.iot.IotFootprintRepository;
import com.soft.app.repository.vcc.iot.IotMachineRepository;
import com.soft.app.service.iot.IotFootprintService;
import com.soft.app.util.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class IotFootprintServiceImpl implements IotFootprintService {
    private static final Logger LOGGER = LogManager.getLogger(IotFootprintServiceImpl.class);
    @Autowired
    private IotFootprintRepository iotFootprintRepository;

    @Autowired
    private IotMachineRepository iotMachineRepository;

    @Autowired
    private IotFootprintMachineRepository iotFootprintMachineRepository;

    @Override
    @Transactional
    public void createOrUpdate(String json) {
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject(json);
        IotFootprint iotFootprint = null;
        if (jsonObject.isNull("footPrint")) {
            iotFootprint = gson.fromJson(json, IotFootprint.class);
            iotFootprintRepository.save(iotFootprint);
        } else {
            iotFootprint = iotFootprintRepository.findById(jsonObject.getLong("footPrint")).get();
            iotFootprint.getIotFootPrintMachines().clear();
            iotFootprintRepository.saveAndFlush(iotFootprint);
        }
        JSONArray jsonArray = jsonObject.getJSONArray("machine");
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject obj = jsonArray.getJSONObject(i);
            long macId = obj.getLong("machine");

            //check is Item
            IotFootprintMachine iotFootprintMachine = iotFootprintMachineRepository.findByIotMachineId(macId);
            if (BeanUtils.isNotNull(iotFootprintMachine)) {
                iotFootprintMachineRepository.delete(iotFootprintMachine);
            }

            //create new Item
            IotMachine iotMachine = iotMachineRepository.findById(macId).get();
            Double posX = obj.getDouble("posX");
            Double posY = obj.getDouble("posY");
            LOGGER.debug("macId {}", macId);
            LOGGER.debug("posX {}", posX);
            LOGGER.debug("posY {}", posY);
            iotFootprintMachine = new IotFootprintMachine();
            iotFootprintMachine.setIotFootprint(iotFootprint);
            iotFootprintMachine.setPosX(posX);
            iotFootprintMachine.setPosY(posY);
            iotFootprintMachine.setIotMachine(iotMachine);
            iotFootprintMachineRepository.save(iotFootprintMachine);
        }
    }
}
