package com.soft.app.aop;

import com.soft.app.spring.security.AuthorizeUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

@Aspect
@Component
public class BaseEntitySaveAspect {
    private static final Logger LOGGER = LogManager.getLogger(BaseEntitySaveAspect.class);

    @Autowired
    AuthorizeUtil authorizeUtil;

    @Pointcut("execution(* org.springframework.data.jpa.repository.**.save*(..)) " +
            "or execution(* org.springframework.data.repository.**.save*(..))")
    public void jpaRepositorySave() {
    }

    @Before("jpaRepositorySave()")
    public void addStampStandardField(JoinPoint jp) {
        String progId = "SYSTEM";
        String user = "SYSTEM";
        String ouCode = "SYSTEM";
        if(com.soft.app.util.BeanUtils.isNotNull(authorizeUtil)){
            user= authorizeUtil.getUserName();
            ouCode=authorizeUtil.getOuCode();
        }
        try {
            for (Object arg : jp.getArgs()) {
                LOGGER.debug("addStandardFields: arg: " + arg.getClass().getName());
                Date currentDate = Calendar.getInstance().getTime();

                if (arg instanceof ArrayList<?>||arg  instanceof HashSet<?>) {
                    for (Object obj : (ArrayList) arg) {
                        assignValueToCommonFields(obj, user, progId, currentDate,ouCode);
                    }
                } else {
                    assignValueToCommonFields(arg, user, progId, currentDate,ouCode);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error : {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private void assignValueToCommonFields(Object arg, String user, String progId, Date currentDate,String ouCode) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        BeanUtils.setProperty(arg, "updatedBy", user);
        BeanUtils.setProperty(arg, "updatedDate", currentDate);
        if (!(BeanUtils.getProperty(arg, "id") != null && BeanUtils.getProperty(arg, "id").trim().length() > 0)) {
            BeanUtils.setProperty(arg, "createdBy", user);
            BeanUtils.setProperty(arg, "createdDate", currentDate);
        }
        BeanUtils.setProperty(arg, "ouCode", ouCode);
    }


}
