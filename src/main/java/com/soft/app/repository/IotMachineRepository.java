package com.soft.app.repository;

import com.soft.app.entity.vcc.iot.IotMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IotMachineRepository extends JpaRepository<IotMachine,Long> {
}
