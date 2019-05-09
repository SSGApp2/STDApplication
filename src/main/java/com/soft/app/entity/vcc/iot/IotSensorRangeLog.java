package com.soft.app.entity.vcc.iot;

import com.soft.app.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "IOT_SENSOR_RANGE_LOG")
public class IotSensorRangeLog extends BaseEntity {

    @Temporal(TemporalType.TIMESTAMP)
    private Date alertTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENSOR_RANGE")
    private IotSensorRange iotSensorRange;
}
