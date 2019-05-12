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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iotMachine")
    IotMachine iotMachine;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "iotSensor")
    private IotSensorRange iotSensorRange;


}
