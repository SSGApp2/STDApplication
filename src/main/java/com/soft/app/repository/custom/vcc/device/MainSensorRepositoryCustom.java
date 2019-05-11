package com.soft.app.repository.custom.vcc.device;

import com.soft.app.entity.vcc.device.MainSensor;

public interface MainSensorRepositoryCustom {
    MainSensor findLastRecordByDeviceCodeOuth(String deviceCode);
}
