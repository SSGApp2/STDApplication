package com.soft.app.repository.custom.vcc.iot.impl;

import com.soft.app.entity.vcc.iot.IotDevice;
import com.soft.app.repository.custom.vcc.iot.IotDeviceRepositoryCustom;
import com.soft.app.spring.security.AuthorizeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class IotDeviceRepositoryCustomImpl implements IotDeviceRepositoryCustom {
    private static final Logger LOGGER = LogManager.getLogger(IotDeviceRepositoryCustomImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    AuthorizeUtil authorizeUtil;

    @Override
    public List<IotDevice> findByOuth() {
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotDevice.class);
        criteria.add(Restrictions.eq("ouCode", authorizeUtil.getOuCode()));
        return criteria.list();
    }
}
