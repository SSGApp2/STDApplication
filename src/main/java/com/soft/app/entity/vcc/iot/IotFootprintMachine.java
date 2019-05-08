package com.soft.app.entity.vcc.iot;

import com.soft.app.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class IotFootprintMachine extends BaseEntity {

    private Double posX;
    private Double posY;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iotFootprint")
    private IotFootprint iotFootprint;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iotMachine")
    private IotMachine iotMachine;



}
