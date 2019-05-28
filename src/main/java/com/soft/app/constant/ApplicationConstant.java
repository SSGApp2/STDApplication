package com.soft.app.constant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationConstant {
    public static final String APPLICATION_NAME = "VCC";
    //    public static final String APPLICATION_VERSION = "v.1.0.20180612";
    public static final String APPLICATION_VERSION = "v." + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

    public static String FLOOR_PRINT="FP";
}
