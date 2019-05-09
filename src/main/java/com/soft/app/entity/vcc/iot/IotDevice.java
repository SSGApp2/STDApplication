package com.soft.app.entity.vcc.iot;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@EqualsAndHashCode(of = { "id" })
@Table(name = "IOT_DEVICE" )
public class IotDevice extends BaseEntity{
	

	@NotNull
	private String macCode;
	
	@NotNull
	private String deviceCode;
	
	@NotNull
	private String deviceName;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iotDevice")
	@JsonManagedReference
	private Set<IotSensor> iotSensor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IOT_MACHINE")
	@JsonBackReference
	IotMachine iotMachine;
}
