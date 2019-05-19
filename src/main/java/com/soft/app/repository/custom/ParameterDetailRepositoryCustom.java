package com.soft.app.repository.custom;

import com.soft.app.entity.app.ParameterDetail;

public interface ParameterDetailRepositoryCustom {
    ParameterDetail findByParameterCodeAndParameterValue1(String code, String parameterValue1);
}
