package com.soft.app.repository.custom.vcc.iot;

import com.soft.app.entity.vcc.iot.IotMachine;

import java.util.List;

public interface IotMachineRepositoryCustom {

    List<IotMachine> findByOuth();

}
