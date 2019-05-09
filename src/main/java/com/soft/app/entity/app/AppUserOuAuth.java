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
public class AppUserOuAuth  extends BaseEntity {
    /**
     */
    private String ouCode;

    /**
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appUser")
    private AppUser appUser;

}
