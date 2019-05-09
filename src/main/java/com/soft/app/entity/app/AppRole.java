package com.soft.app.entity.app;

import com.soft.app.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
public class AppRole extends BaseEntity {
    /**
     */
    private String roleCode;
    /**
     */
    private String roleName;
    /**
     */
    private String description;
    /**
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appRole")
    private Set<AppUserRole> appUserRoles = new HashSet<AppUserRole>();
}
