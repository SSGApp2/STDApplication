package com.soft.app.entity.app;

import com.soft.app.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
public class AppUserRole extends BaseEntity {

    /**
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appUser")
    private AppUser appUser;

    /**
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appRole")
    private AppRole appRole;

}
