package com.soft.app.entity.vcc.iot;

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
@Table(name = "IOT_MACHINE")
public class IotMachine extends BaseEntity {


    @NotNull
    private String ouCode;

    @NotNull
    private String divCode;

    @NotNull
    private String macTypeCode;

    @NotNull
    private String macCode;

    @NotNull
    private String macName;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iotMachine")
    @JsonManagedReference
    private Set<IotDevice> iotDevice;

}
