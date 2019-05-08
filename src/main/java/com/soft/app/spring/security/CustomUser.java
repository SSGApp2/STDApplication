package com.soft.app.spring.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

@Data
public class CustomUser extends User {

    private String accessToken ="";
    private Date expireTime;
    private String roles ;
    private String firstNameEng;


    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }
    public boolean isAccountNonLocked() {
        return true;
    }
}
