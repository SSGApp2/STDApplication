package com.soft.app.repository.vcc.iot;

import com.soft.app.entity.vcc.iot.IotFootprintMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IotFootprintMachineRepository extends JpaRepository<IotFootprintMachine,Long> {
   public IotFootprintMachine findByIotMachineId(Long id);
}
