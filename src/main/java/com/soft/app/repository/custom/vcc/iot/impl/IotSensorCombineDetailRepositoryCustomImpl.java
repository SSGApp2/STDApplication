package com.soft.app.repository.custom.vcc.iot.impl;

import com.soft.app.entity.vcc.iot.IotSensorCombineDetail;
import com.soft.app.repository.custom.vcc.iot.IotSensorCombineDetailRepositoryCustom;
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
public class IotSensorCombineDetailRepositoryCustomImpl implements IotSensorCombineDetailRepositoryCustom {
    private static final Logger LOGGER = LogManager.getLogger(IotFootprintRepositoryCustomImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    AuthorizeUtil authorizeUtil;

    public List<IotSensorCombineDetail> findBySensorCombineID(Long id) {
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotSensorCombineDetail.class)
                .createAlias("iotSensorCombine", "iotSensorCombine");
        criteria.add(Restrictions.eq("iotSensorCombine.id", id));
        return criteria.list();
    }
}
