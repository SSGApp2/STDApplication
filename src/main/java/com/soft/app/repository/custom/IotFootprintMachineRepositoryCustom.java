package com.soft.app.repository.custom;

import com.soft.app.entity.vcc.iot.IotFootprintMachine;

import java.util.List;

public interface IotFootprintMachineRepositoryCustom {
    List<IotFootprintMachine> findByIotFootprintOuth(Long id);
    List<IotFootprintMachine> findByMachineId(Long id);
}
