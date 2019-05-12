package com.soft.app.repository.custom.vcc.iot;


import com.soft.app.entity.vcc.iot.IotSensor;

import java.util.List;

public interface IotSensorRepositoryCustom {
    List<IotSensor> findByIotDeviceCodeOth(String deviceCode);
}
