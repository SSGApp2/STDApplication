package com.soft.app.repository;

import com.soft.app.entity.app.ParameterHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterHeaderRepository extends JpaRepository<ParameterHeader, Long> {

    ParameterHeader findByCode(String code);
}
