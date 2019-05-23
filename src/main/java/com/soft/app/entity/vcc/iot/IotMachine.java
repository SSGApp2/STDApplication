package com.soft.app.entity.vcc.iot;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soft.app.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class IotMachine extends BaseEntity{

    @NotNull
    private String macName;

    private String lineToken;

    private String description;


    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private IotDevice iotDevice;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "iotMachine")
    private List<IotSensor> iotSensor;

    public void addIotSensor(IotSensor iotSensor){
        iotSensor.setIotMachine(this);
        this.iotSensor.add(iotSensor);
    }

    private String ouCode;

}
