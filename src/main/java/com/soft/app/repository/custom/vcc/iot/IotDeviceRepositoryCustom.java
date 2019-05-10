package com.soft.app.repository.custom.vcc.iot;

import com.soft.app.entity.vcc.iot.IotDevice;

import java.util.List;

public interface IotDeviceRepositoryCustom {

    List<IotDevice> findByOuth();

}
