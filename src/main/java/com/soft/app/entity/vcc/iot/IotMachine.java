package com.soft.app.entity.vcc.iot;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.soft.app.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class IotMachine extends BaseEntity{

    private String macCode;

    @NotNull
    private String macName;

    private String lineToken;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private IotDevice iotDevice;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iotMachine")
    private Set<IotSensor> iotSensor;

    private String ouCode;

}
