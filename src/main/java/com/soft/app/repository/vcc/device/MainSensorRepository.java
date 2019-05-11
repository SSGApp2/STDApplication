package com.soft.app.repository.vcc.device;

import com.soft.app.entity.vcc.device.MainSensor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MainSensorRepository extends MongoRepository<MainSensor,String> {

}
