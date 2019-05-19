package com.soft.app.spring.configuration;

import com.soft.app.constant.ServerConstant;
import com.soft.app.repository.ParameterHeaderRepository;
import com.soft.app.repository.custom.ParameterDetailRepositoryCustom;
import com.soft.app.repository.vcc.iot.IotSensorCombineDetailRepository;
import com.soft.app.repository.vcc.iot.IotSensorCombineRepository;
import com.soft.app.repository.vcc.iot.IotSensorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationStartup.class);

    @Autowired
    private ParameterHeaderRepository parameterHeaderRepository;

    @Autowired
    private IotSensorCombineRepository iotSensorCombineRepository;

    @Autowired
    private IotSensorRepository iotSensorRepository;

    @Autowired
    private IotSensorCombineDetailRepository iotSensorCombineDetailRepository;

    @Autowired
    private ParameterDetailRepositoryCustom parameterDetailRepositoryCustom;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        LOGGER.info("ApplicationStartup.....!");
        LOGGER.info("Swagger UI : /swagger-ui.html");
        LOGGER.info("Spring Data REST : /rest-api");

        ServerConstant.VCCWebSocketServer = parameterDetailRepositoryCustom.findByParameterCodeAndParameterValue1("50", "VCCWebSocketServer").getParameterValue2();
        ServerConstant.VCCJobEngine = parameterDetailRepositoryCustom.findByParameterCodeAndParameterValue1("50", "VCCJobEngine").getParameterValue2();

//        IotSensor iotSensor = iotSensorRepository.findAll().get(0);
//        IotSensor iotSensor2 = iotSensorRepository.findAll().get(1);
//
//
//        IotSensorCombine iotSensorCombine = new IotSensorCombine();
//        iotSensorCombine.setProfileName("Test");
//        iotSensorCombineRepository.save(iotSensorCombine);
//
//
//        IotSensorCombineDetail iotSensorCombineDetail = new IotSensorCombineDetail();
//        iotSensorCombineDetail.setIotSensor(iotSensor);
//        iotSensorCombineDetail.setIotSensorCombine(iotSensorCombine);
//        iotSensorCombineDetailRepository.save(iotSensorCombineDetail);
//
//        IotSensorCombineDetail iotSensorCombineDetail2 = new IotSensorCombineDetail();
//        iotSensorCombineDetail2.setIotSensor(iotSensor2);
//        iotSensorCombineDetail2.setIotSensorCombine(iotSensorCombine);
//        iotSensorCombineDetailRepository.save(iotSensorCombineDetail2);


//        Demo
//        appParameterRepository.deleteAll();
//        AppParameter appParameter=new AppParameter();
//        appParameter.setCode("50");
//        appParameter.setParameterDescription("ConfigForApplication");
//
//        Set<ParameterDetail> parameterDetails=new HashSet<>();
//        ParameterDetail parameterDetail=new ParameterDetail();
//        parameterDetail.setAppParameter(appParameter);
//        parameterDetail.setCode("01");
//        parameterDetail.setParameterValue1("EngineServer");
//        parameterDetail.setParameterValue2("localhost:8888");
//        parameterDetails.add(parameterDetail);
//        appParameter.setParameterDetails(parameterDetails);
//        appParameterRepository.save(appParameter);

    }

}
