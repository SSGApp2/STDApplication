package com.soft.app.repository.custom.vcc.iot.impl;

import com.soft.app.entity.vcc.iot.IotFootprintMachine;
import com.soft.app.entity.vcc.iot.IotMachine;
import com.soft.app.repository.custom.vcc.iot.IotMachineRepositoryCustom;
import com.soft.app.spring.security.AuthorizeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class IotMachineRepositoryCustomImpl implements IotMachineRepositoryCustom {
    private static final Logger LOGGER = LogManager.getLogger(IotMachineRepositoryCustomImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    AuthorizeUtil authorizeUtil;

    @Override
    public List<IotMachine> findByOuth() {
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotMachine.class,"iotM").createAlias("iotM.iotDevice","iod");
        criteria.add(Restrictions.eq("iod.ouCode", authorizeUtil.getOuCode()));
        criteria.addOrder(Order.asc("macName"));
        return criteria.list();
    }

    @Override
    public List<IotMachine> findByNotInFootprintOuth() {
        String ouCode=authorizeUtil.getOuCode();
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotMachine.class);
        criteria.createAlias("iotDevice","IotDevice");
//      Find Id in used
        DetachedCriteria de = DetachedCriteria.forClass(IotFootprintMachine.class);
        de.createAlias("iotMachine","IotMachine");
        de.add(Restrictions.eq("IotMachine.ouCode",ouCode));
        ProjectionList pro = Projections.projectionList();
        pro.add(Projections.property("IotMachine.id"));
        de.setProjection(pro);
        Criterion c1 = Subqueries.propertyNotIn("id", de);
        criteria.add(c1);
        criteria.add(Restrictions.eq("ouCode",ouCode));
        criteria.addOrder(Order.asc("IotDevice.deviceName"));
        return criteria.list();

    }

    @Override
    public IotMachine findByIdOuth(Long id) {
        String ouCode=authorizeUtil.getOuCode();
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotMachine.class);
        criteria.add(Restrictions.eq("ouCode",ouCode));
        criteria.add(Restrictions.eq("id",id));
        return (IotMachine)criteria.uniqueResult();
    }

    @Override
    public IotMachine findByDeviceId(Long id){
        String ouCode=authorizeUtil.getOuCode();
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotMachine.class)
                .createAlias("iotDevice", "device");
        criteria.add(Restrictions.eq("ouCode",ouCode));
        criteria.add(Restrictions.eq("device.id",id));
        return (IotMachine)criteria.uniqueResult();
    }
}
