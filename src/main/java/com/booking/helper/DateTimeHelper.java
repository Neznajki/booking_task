package com.booking.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {
    public static String formatDate(Date date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);
    }

    public static String formatDateTime(Date date) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formatter.format(date);
    }
}
