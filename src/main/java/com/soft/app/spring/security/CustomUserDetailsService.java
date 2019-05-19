package com.soft.app.spring.security;

import com.google.gson.Gson;
import com.soft.app.entity.app.*;
import com.soft.app.repository.AppOuAuthRepository;
import com.soft.app.repository.AppUserOuAuthRepository;
import com.soft.app.repository.AppUserRepository;
import com.soft.app.repository.ParameterHeaderRepository;
import com.soft.app.repository.custom.AppUserRepositoryCustom;
import com.soft.app.util.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService, PasswordEncoder {
    private static final Logger LOGGER = LogManager.getLogger(CustomUserDetailsService.class);


    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppUserOuAuthRepository appUserOuAuthRepository;

    @Autowired
    AppUserRepositoryCustom appUserRepositoryCustom;

    @Autowired
    AppOuAuthRepository appOuAuthRepository;

    @Autowired
    AuthorizeUtil authorizeUtil;


    @Autowired
    private ParameterHeaderRepository parameterHeaderRepository;

    //Default Attribute
    private String userName;
    private String password;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            AppUser appUser = appUserRepository.findByUsername(userName);
            CustomUser user = new CustomUser(userName, appUser.getPassword(), new ArrayList<>());
            this.userName = userName;
            if (BeanUtils.isNotNull(appUser)) {
                LOGGER.info("---------------------------------");
                LOGGER.info("----   loadUserByUsername   -----");
                LOGGER.info("UserName : ===================>{}", userName);
                /* Support Detect for Tomcat Attribute */
                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

                List<AppUserOuAuth> appUserOuAuthList = appUserOuAuthRepository.findByAppUser(appUser);

                authorizeUtil.setUserName(user.getUsername());
                List<AppOuAuth> ouAuths = new ArrayList<>();
                if (BeanUtils.isNotEmpty(appUserOuAuthList)) {
                    authorizeUtil.setOuCode(appUserOuAuthList.get(0).getOuCode()); //set First
                    for (AppUserOuAuth appUserOuAuth : appUserOuAuthList) {
                        ouAuths.add(appOuAuthRepository.findByOuCode(appUserOuAuth.getOuCode()));
                    }
                    authorizeUtil.setRoleCode("user");
                } else {
                    authorizeUtil.setRoleCode("admin");
                    ouAuths = appOuAuthRepository.findAll();
                }

                /* Add to Bean SESSION SCOPE */
                Gson gson=new Gson();
                //initial Data
                attr.getRequest().getSession(true).setAttribute("userName", userName);
                attr.getRequest().getSession(true).setAttribute("ouAuths", ouAuths);

                ParameterHeader appParameterConfig = parameterHeaderRepository.findByCode("50");
                Map mapServerConstant=new HashMap();
                for(ParameterDetail parameterDetail:appParameterConfig.getParameterDetails()){
                    mapServerConstant.put(parameterDetail.getParameterValue1(),parameterDetail.getParameterValue2());
                }
                attr.getRequest().getSession(true).setAttribute("ServerConstant", gson.toJson(mapServerConstant));


                LOGGER.info("session : " + attr.getRequest().getSession().getId());
                return new org.springframework.security.core.userdetails.User(userName, user.getAccessToken(),
                        true, user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());

            } else {
                LOGGER.error("No user with username " + userName + "' found!");
                throw new UsernameNotFoundException("No user with username '" + userName + "' found!");
            }

        } catch (Exception e) {
            LOGGER.error("error : {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean matches(CharSequence rawPassword, String s) {
        this.password = rawPassword.toString();
        LOGGER.info("---------------------------------");
        LOGGER.info("---- Match with database authentication ----");
        return appUserRepositoryCustom.isUserNamePassword(this.userName, this.password);
    }
}
