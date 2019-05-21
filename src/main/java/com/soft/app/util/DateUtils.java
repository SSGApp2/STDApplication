package com.soft.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");

    public static String toStringWithoutTime(Date date) {
        return spf.format(date);
    }

}
