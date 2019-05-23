package com.soft.app.repository.custom.vcc.iot;

import com.soft.app.entity.vcc.iot.IotSensorCombineDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IotSensorCombineDetailRepositoryCustom{
  List<IotSensorCombineDetail> findBySensorCombineID(Long id);
}
