package com.soft.app.entity.vcc.iot.custom;

import com.soft.app.entity.vcc.iot.IotSensorCombineDetail;
import lombok.Data;

import java.util.List;

@Data
public class IotSensorCombineCustom {
    private String profileName;
    private String alertType;
    private Integer repeatAlert;
    private String repeatUnit;
    private String alertMessage;
    private List<IotSensorCombineDetail> iotSensorCombineDetails;
}
