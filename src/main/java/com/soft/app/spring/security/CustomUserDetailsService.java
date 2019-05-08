package com.soft.app.spring.security;

import com.soft.app.entity.app.AppUser;
import com.soft.app.entity.app.AppUserOuAuth;
import com.soft.app.entity.app.AppUserRole;
import com.soft.app.repository.AppUserOuAuthRepository;
import com.soft.app.repository.AppUserRepository;
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
import java.util.List;

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
    AuthorizeUtil authorizeUtil;

    //Default Attribute
    private String userName;
    private String password;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            AppUser appUser = appUserRepository.findByUsername(userName);
            if (BeanUtils.isNotNull(appUser)) {
                LOGGER.info("---------------------------------");
                LOGGER.info("----   loadUserByUsername   -----");
                LOGGER.info("UserName : ===================>{}", userName);

                CustomUser user = new CustomUser(userName, appUser.getPassword(), new ArrayList<>());
                this.userName = user.getUsername();

                List<AppUserOuAuth> appUserOuAuthList = appUserOuAuthRepository.findByAppUser(appUser);
                if (BeanUtils.isNotEmpty(appUserOuAuthList)) {
                    /* Add to Bean SESSION SCOPE */
                    authorizeUtil.setUserName(user.getUsername());
                    authorizeUtil.setUserName(appUserOuAuthList.get(0).getOuCode());

                    //initial Data

                    /* Support Detect for Tomcat Attribute */
                    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                    attr.getRequest().getSession(true).setAttribute("userName", userName);

                    LOGGER.info("session : " + attr.getRequest().getSession().getId());
                    return user;

                } else {
                    LOGGER.error("No OU with username '" + userName + "' found!");
                    throw new UsernameNotFoundException("No OU with username '" + userName + "' found!");
                }

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
    public boolean matches(CharSequence rawPassword, String s) {
        this.password = rawPassword.toString();
        LOGGER.info("---------------------------------");
        LOGGER.info("---- Match with database authentication ----");
        return appUserRepositoryCustom.isUserNamePassword(this.userName, this.password);
    }
}
