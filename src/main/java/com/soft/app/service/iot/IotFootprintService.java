package com.soft.app.service.iot;

import com.soft.app.entity.vcc.iot.IotFootprint;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

public interface IotFootprintService {
    public IotFootprint createOrUpdate(MultipartHttpServletRequest multipartHttpServletRequest);
}
