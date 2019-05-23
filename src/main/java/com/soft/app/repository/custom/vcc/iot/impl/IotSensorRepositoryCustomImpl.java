package com.soft.app.repository.custom.vcc.iot.impl;

import com.soft.app.entity.vcc.iot.IotSensor;
import com.soft.app.repository.custom.vcc.iot.IotSensorRepositoryCustom;
import com.soft.app.spring.security.AuthorizeUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class IotSensorRepositoryCustomImpl implements IotSensorRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    AuthorizeUtil authorizeUtil;


    @Override
    public List<IotSensor> findByMachineIdOth(Long id) {
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotSensor.class);
        criteria.createAlias("iotMachine", "IotMachine");
        criteria.add(Restrictions.eq("IotMachine.ouCode", authorizeUtil.getOuCode()));
        criteria.add(Restrictions.eq("IotMachine.id", id));
        criteria.addOrder(Order.asc("seq"));
        return criteria.list();
    }

    @Override
    public IotSensor findByMachineCodeAndSensorCodeOth(String sensorCode) {
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotSensor.class);
        criteria.createAlias("iotMachine", "IotMachine");
        criteria.add(Restrictions.eq("IotMachine.ouCode", authorizeUtil.getOuCode()));
        criteria.add(Restrictions.eq("IotMachine.id", 11L));
        criteria.add(Restrictions.eq("sensorCode", sensorCode));
        return (IotSensor) criteria.uniqueResult();
    }

    @Override
    public IotSensor findByIdOth(Long id) {
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotSensor.class);
        criteria.createAlias("iotMachine", "IotMachine");
        criteria.add(Restrictions.eq("IotMachine.ouCode", authorizeUtil.getOuCode()));
        criteria.add(Restrictions.eq("id",id));
        return (IotSensor) criteria.uniqueResult();
    }
}
