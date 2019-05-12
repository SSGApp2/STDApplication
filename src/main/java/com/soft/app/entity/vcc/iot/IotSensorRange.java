package com.soft.app.entity.vcc.iot;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soft.app.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class IotSensorRange extends BaseEntity{

    private Double dangerAmt;
    private Integer dangerAlert;
    private String dangerUnit;

    private Double warningAmt;
    private Integer warningAlert;
    private String warningUnit;

    private String valueType;
    private String displayType;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iotSensor")
    IotSensor iotSensor;
}
