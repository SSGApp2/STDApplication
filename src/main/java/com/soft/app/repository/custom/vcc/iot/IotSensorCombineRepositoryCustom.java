package com.soft.app.repository.custom.vcc.iot;

import com.soft.app.entity.vcc.iot.IotSensorCombine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IotSensorCombineRepositoryCustom{
    List<IotSensorCombine> findDetailAllByID(Long id);
}
