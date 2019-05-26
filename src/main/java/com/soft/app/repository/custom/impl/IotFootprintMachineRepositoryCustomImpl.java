package com.soft.app.repository.custom.impl;

import com.soft.app.entity.app.ParameterDetail;
import com.soft.app.entity.vcc.iot.IotFootprintMachine;
import com.soft.app.repository.custom.IotFootprintMachineRepositoryCustom;
import com.soft.app.spring.security.AuthorizeUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class IotFootprintMachineRepositoryCustomImpl implements IotFootprintMachineRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    AuthorizeUtil authorizeUtil;


    @Override
    public List<IotFootprintMachine> findByIotFootprintOuth(Long id) {
        String ouCode = authorizeUtil.getOuCode();
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(IotFootprintMachine.class);
        criteria.createAlias("iotFootprint", "IotFootprint");
        criteria.createAlias("iotMachine", "IotMachine");
        criteria.createAlias("iotMachine.iotDevice", "IotDevice");
        criteria.add(Restrictions.eq("IotFootprint.ouCode", ouCode));
        criteria.add(Restrictions.eq("IotMachine.ouCode", ouCode));
        criteria.add(Restrictions.eq("IotFootprint.id", id));
        return criteria.list();
    }
}
