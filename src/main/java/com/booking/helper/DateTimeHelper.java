package com.booking.helper;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

public class DateTimeHelper {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final FastDateFormat fdf = FastDateFormat.getInstance(DATE_FORMAT);


    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }

        return fdf.format(date);
    }
}
