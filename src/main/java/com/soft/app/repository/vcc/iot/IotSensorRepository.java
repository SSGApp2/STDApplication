package com.soft.app.repository.vcc.iot;

import com.soft.app.entity.vcc.iot.IotSensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IotSensorRepository extends JpaRepository<IotSensor, Long> {

}
