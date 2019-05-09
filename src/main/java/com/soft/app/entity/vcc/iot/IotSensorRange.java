package com.soft.app.entity.vcc.iot;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soft.app.entity.base.BaseEntity;
import com.soft.app.util.BeanUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "IOT_SENSOR_RANGE")
public class IotSensorRange extends BaseEntity {

    @NotNull
    private String deviceCode;

    @NotNull
    private String sensorCode;

    @NotNull
    @Column(precision = 19, scale = 5)
    private Double normalVal;

    @Column(precision = 19, scale = 5)
    private Double warningPercent;

    @Column(precision = 19, scale = 5)
    private Double warningAmt;

    @Column(precision = 19, scale = 5)
    private Double dangerPercent;

    @Column(precision = 19, scale = 5)
    private Double dangerAmt;

    @NotNull
    private String displayType;

    private Integer warningAlert;

    private Integer dangerAlert;

    private String warningUnit;

    private String dangerUnit;

    private String valueType;

    protected Integer displayOrder;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IOT_SENSOR")
    @JsonBackReference
    IotSensor iotSensor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "iotSensorRange")
    private Set<IotSensorRangeLog> iotSensorRangeLogs;

    //for check status
    public String getSensorStatus(Double currentValue) {
        Double warningValue = null;
        Double dangerValue = null;
        String message = null;

        if (String.valueOf(this.valueType).equals("0")) {
            //Percent
            if (BeanUtils.isNotNull(this.dangerPercent)) {
                dangerValue = (this.dangerPercent / 100D * this.normalVal);
            }
            if (BeanUtils.isNotNull(this.warningPercent)) {
                warningValue = (this.warningPercent / 100D * this.normalVal);
            }
        } else {
            //Amount
            if (BeanUtils.isNotNull(this.dangerAmt)) {
                dangerValue = this.dangerAmt;
            }
            if (BeanUtils.isNotNull(this.warningAmt)) {
                warningValue = this.warningAmt;
            }
        }

        if ("N".equals(this.displayType)) {
            if (BeanUtils.isNotNull(dangerValue) && (currentValue <= normalVal - dangerValue)) {
                message = "danger";
            } else if (BeanUtils.isNotNull(warningValue) && (currentValue <= normalVal - warningValue)) {
                message = "warning";
            }
        } else if ("P".equals(displayType)) {
            if (BeanUtils.isNotNull(dangerValue) && (currentValue >= normalVal + dangerValue)) {
                message = "danger";
            } else if (BeanUtils.isNotNull(warningValue) && (currentValue >= normalVal + warningValue)) {
                message = "warning";
            }
        } else {
            if (BeanUtils.isNotNull(dangerValue) && (currentValue <= normalVal - dangerValue)) {
                message = "danger";
            } else if (BeanUtils.isNotNull(warningValue) && currentValue <= normalVal - warningValue) {
                message = "warning";
            } else if (BeanUtils.isNotNull(dangerValue) && (currentValue >= normalVal + dangerValue)) {
                message = "danger";
            } else if (BeanUtils.isNotNull(warningValue) && (currentValue >= normalVal + warningValue)) {
                message = "warning";
            }
        }
        return message;
    }

    @JsonIgnore
    public Boolean isRepeatDanger() {
        if (BeanUtils.isNotNull(this.dangerAlert) && BeanUtils.isNotNull(this.dangerUnit)) {
            return true;
        } else {
            return false;
        }
    }

    @JsonIgnore
    public Boolean isRepeatWarning() {
        if (BeanUtils.isNotNull(this.warningAlert) && BeanUtils.isNotNull(this.warningUnit)) {
            return true;
        } else {
            return false;
        }
    }

    @JsonIgnore
    public String getMessageRepeatAlert(String status) {
        String result = null;
        Integer timeAlert = null;
        String timeAlertUnit=null;
        if (this.isRepeatDanger() || this.isRepeatWarning()) {
            if (String.valueOf(status).equals("danger")) {
                timeAlert = this.dangerAlert;
                timeAlertUnit = this.dangerUnit;
            } else if (String.valueOf(status).equals("warning")) {
                timeAlert = this.warningAlert;
                timeAlertUnit = this.warningUnit;
            }
        }
        if (BeanUtils.isNotNull(timeAlert) && BeanUtils.isNotNull(timeAlertUnit)) {
            if (timeAlertUnit.equals("H")) {
                result="Alert every : "+timeAlert+" hours";
            } else if (timeAlertUnit.equals("M")) {
                result="Alert every : "+timeAlert+" minutes";
            } else if (timeAlertUnit.equals("S")) {
                result="Alert every : "+timeAlert+" seconds";
            }
        }
        result="";
        return result;
    }
}
