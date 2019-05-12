package com.soft.app.repository.custom.vcc.iot.impl;

import com.soft.app.entity.vcc.iot.IotSensor;
import com.soft.app.repository.custom.vcc.iot.IotSensorRepositoryCustom;
import com.soft.app.spring.security.AuthorizeUtil;
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
public class IotSensorRepositoryCustomImpl implements IotSensorRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    AuthorizeUtil authorizeUtil;

    @Override
    public List<IotSensor> findByIotDeviceCodeOth(String deviceCode) {
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotSensor.class);
        criteria.createAlias("iotMachine","IotMachine");
        criteria.createAlias("IotMachine.iotDevice","IotDevice");
        criteria.add(Restrictions.eq("IotDevice.ouCode", authorizeUtil.getOuCode()));
        criteria.add(Restrictions.eq("IotDevice.deviceCode",deviceCode));
        criteria.addOrder(Order.asc("seq"));
        return criteria.list();
    }
}
