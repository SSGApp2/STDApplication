package com.soft.app.repository.custom.vcc.device.impl;

import com.soft.app.entity.vcc.device.MainSensor;
import com.soft.app.repository.custom.vcc.device.MainSensorRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;

@Repository
public class MainSensorRepositoryCustomImpl implements MainSensorRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public MainSensor findLastRecordByDeviceCodeOuth(String deviceCode) {
        Query query = new Query();
        query.limit(1);
        query.with(new Sort(Sort.Direction.DESC, "_id"));
        query.addCriteria(Criteria.where("DeviceName").is(deviceCode));
        List<MainSensor> data= mongoTemplate.find(query, MainSensor.class);
        return data.get(0);
    }
}
