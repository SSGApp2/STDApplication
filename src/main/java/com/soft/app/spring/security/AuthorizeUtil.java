package com.soft.app.spring.security;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class AuthorizeUtil implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizeUtil.class);

    private String ouCode;
    private String userName;
    private String roleCode;

}
