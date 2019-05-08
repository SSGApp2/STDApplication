package com.soft.app.repository.vcc.iot;

import com.soft.app.entity.vcc.iot.IotSensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IotSensorRepository extends JpaRepository<IotSensor, Long> {
}
