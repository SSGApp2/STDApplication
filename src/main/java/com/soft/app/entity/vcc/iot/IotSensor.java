package com.soft.app.entity.vcc.iot;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iotDevice")
    IotDevice iotDevice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iotSensor")
    private Set<IotSensorRange> iotSensorRange;

}
