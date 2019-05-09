package com.soft.app.entity.vcc.iot;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.soft.app.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "IOT_SENSOR")
public class IotSensor extends BaseEntity {

    @NotNull
    private String deviceCode;

    @NotNull
    private String sensorCode;
    @NotNull
    private String sensorName;

    private String unit;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iotSensor")
    @JsonManagedReference
    private IotSensorRange iotSensorRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IOT_DEVICE")
    @JsonBackReference
    IotDevice iotDevice;
}
