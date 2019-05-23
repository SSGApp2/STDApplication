package com.soft.app.repository.custom.vcc.iot;


import com.soft.app.entity.vcc.iot.IotSensor;

import java.util.List;

public interface IotSensorRepositoryCustom {

    List<IotSensor> findByMachineIdOth(Long id);
    IotSensor findByMachineCodeAndSensorCodeOth(String sensorCode);
    IotSensor findByIdOth(Long id);
}
