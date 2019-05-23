package com.soft.app.repository.custom.vcc.iot.impl;

import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.entity.vcc.iot.IotSensorCombine;
import com.soft.app.entity.vcc.iot.IotSensorCombineDetail;
import com.soft.app.repository.custom.vcc.iot.IotSensorCombineRepositoryCustom;
import com.soft.app.spring.security.AuthorizeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository
public class IotSensorCombineRepositoryCustomImpl implements IotSensorCombineRepositoryCustom{

    private static final Logger LOGGER = LogManager.getLogger(IotSensorCombineRepositoryCustomImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    AuthorizeUtil authorizeUtil;

    @Override
    public List<Map> findDetailAllByOuth(){
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotSensorCombine.class,"iotsc")
                .createAlias("iotSensorCombineDetails","iotscd")
                .createAlias("iotscd.iotSensor","iots")
                .createAlias("iots.iotMachine", "iotm");

        criteria.add(Restrictions.eq("iotm.ouCode", authorizeUtil.getOuCode()));

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("iots.sensorName"), "sensorName");
        projectionList.add(Projections.property("iots.id"), "sensorID");
        projectionList.add(Projections.property("iotscd.id"), "combineDetailID");
        projectionList.add(Projections.property("iotsc.id"),"combineID");
        criteria.setProjection(projectionList);
        criteria.addOrder(Order.asc("combineDetailID"));
        criteria.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); //TOMAP

        return criteria.list();
    }
}