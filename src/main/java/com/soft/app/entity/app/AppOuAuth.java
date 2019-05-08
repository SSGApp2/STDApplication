package com.soft.app.entity.app;

import com.soft.app.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "OU")
public class AppOuAuth extends BaseEntity{
    /**
     */
    private String ouCode;

    /**
     */
    private String ouName;
}
