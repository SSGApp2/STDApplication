package com.soft.app.entity.vcc.iot;

import com.soft.app.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class IotSensorCombine extends BaseEntity {

    private String profileName;
    private String alertType;
    private Integer repeatAlert;
    private String repeatUnit;
    private String alertMessage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iotSensorCombine")
    private Set<IotSensorCombineDetail> iotSensorCombineDetails;
}
