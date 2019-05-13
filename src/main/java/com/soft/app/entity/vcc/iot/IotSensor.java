package com.soft.app.entity.vcc.iot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soft.app.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class IotSensor extends BaseEntity{

    private String sensorCode;

    private String sensorName;

    private String sensorUnit ;

    private Double normalValue;

    private Integer seq;

    private Double dangerAmt;
    private Integer dangerAlert;
    private String dangerUnit;

    private Double warningAmt;
    private Integer warningAlert;
    private String warningUnit;

    private String valueType;
    private String displayType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iotMachine")
    IotMachine iotMachine;

}
