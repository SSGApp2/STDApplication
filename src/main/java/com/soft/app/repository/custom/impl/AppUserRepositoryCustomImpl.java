package com.soft.app.repository.custom.impl;

import com.soft.app.entity.app.AppUser;
import com.soft.app.repository.custom.AppUserRepositoryCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AppUserRepositoryCustomImpl implements AppUserRepositoryCustom {
    private static final Logger LOGGER = LogManager.getLogger(AppUserRepositoryCustomImpl.class);
    @PersistenceContext
    private EntityManager em;


    @Override
    public Boolean isUserNamePassword(String username, String password) {
        Criteria criteria = ((Session) em.getDelegate()).createCriteria(AppUser.class);
        criteria.add(Restrictions.eq("username", username));
        criteria.add(Restrictions.eq("password", password));
        criteria.setProjection(Projections.rowCount());
        Long size = (Long) criteria.uniqueResult();
        if (size > 0) {
            return true;
        } else {
            return false;
        }
    }
}
