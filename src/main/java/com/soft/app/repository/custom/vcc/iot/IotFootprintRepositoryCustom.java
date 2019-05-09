package com.soft.app.repository.custom.vcc.iot;

import com.soft.app.entity.vcc.iot.IotFootprint;

import java.util.List;

public interface IotFootprintRepositoryCustom {
    List<IotFootprint> findByOuth();
}
