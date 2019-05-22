package com.soft.app.repository.custom.vcc.iot.impl;

import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.entity.vcc.iot.IotSensorCombine;
import com.soft.app.repository.custom.vcc.iot.IotSensorCombineRepositoryCustom;
import com.soft.app.spring.security.AuthorizeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class IotSensorCombineRepositoryCustomImpl implements IotSensorCombineRepositoryCustom{

    private static final Logger LOGGER = LogManager.getLogger(IotMachineRepositoryCustomImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    AuthorizeUtil authorizeUtil;

    @Override
    public List<IotSensorCombine> findDetailAllByID(Long id){
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotSensorCombine.class)
                .createAlias("iotSensorCombineDetails","iod");
        return criteria.list();
    }
}
