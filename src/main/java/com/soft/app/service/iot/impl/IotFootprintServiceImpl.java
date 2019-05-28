package com.soft.app.service.iot.impl;

import com.google.gson.Gson;
import com.soft.app.constant.ApplicationConstant;
import com.soft.app.entity.vcc.iot.IotFootprint;
import com.soft.app.entity.vcc.iot.IotFootprintMachine;
import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.repository.vcc.iot.IotFootprintMachineRepository;
import com.soft.app.repository.vcc.iot.IotFootprintRepository;
import com.soft.app.repository.vcc.iot.IotMachineRepository;
import com.soft.app.service.FileUtilsService;
import com.soft.app.service.iot.IotFootprintService;
import com.soft.app.util.BeanUtils;
import com.soft.app.util.UUIDUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class IotFootprintServiceImpl implements IotFootprintService {
    private static final Logger LOGGER = LogManager.getLogger(IotFootprintServiceImpl.class);
    @Autowired
    private IotFootprintRepository iotFootprintRepository;

    @Autowired
    private IotMachineRepository iotMachineRepository;

    @Autowired
    private IotFootprintMachineRepository iotFootprintMachineRepository;

    @Autowired
    private FileUtilsService fileUtilsService;

    @Override
    @Transactional
    public IotFootprint createOrUpdate(MultipartHttpServletRequest multipartHttpServletRequest) {
        try {
            Gson gson = new Gson();
            MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
            String json = multipartHttpServletRequest.getParameter("json");
            JSONObject jsonObject = new JSONObject(json);
            IotFootprint iotFootprint = null;
            String picName = null;

            if (jsonObject.isNull("footPrint")) {

                if (BeanUtils.isNotNull(multipartFile)) {
                    //have Image
                    picName = UUIDUtils.timeBasedGenerator() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                    byte[] bytes = multipartFile.getBytes();
                    fileUtilsService.saveFileModule(picName,bytes,ApplicationConstant.FLOOR_PRINT);
                }

                iotFootprint = gson.fromJson(json, IotFootprint.class);
                iotFootprint.setPicName(picName);
                iotFootprintRepository.save(iotFootprint);
            } else {
                iotFootprint = iotFootprintRepository.findById(jsonObject.getLong("footPrint")).get();
                iotFootprint.setName(jsonObject.getString("name"));
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
            return iotFootprint;
        } catch (Exception e) {
            LOGGER.debug("ERROR {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
