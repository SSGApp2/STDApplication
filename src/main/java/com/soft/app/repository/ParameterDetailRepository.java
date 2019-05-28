package com.soft.app.repository;

import com.soft.app.entity.app.ParameterDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParameterDetailRepository extends JpaRepository<ParameterDetail, Long> {

    @Query("select o from ParameterDetail o where o.parameterHeader.code =:headerCode and o.code =:code")
    ParameterDetail findByCodeAndHeaderCode(@Param(value = "code") String code, @Param(value = "headerCode") String headerCode);
}
