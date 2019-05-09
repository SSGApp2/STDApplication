package com.soft.app.entity.app;

import com.soft.app.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
public class AppUser extends BaseEntity {
    /**
     */
    private String username;

    /**
     */
    private String password;

    /**
     */
    private String description;

    /**
     */
    private String userEmail;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser", orphanRemoval = true)
    private Set<AppUserRole> appUserRoles = new HashSet<AppUserRole>();
}
