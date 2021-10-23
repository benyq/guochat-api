package com.benyq.guochatapi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

    private static final String DATE_REG = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Long dateToLong(String date) {
        if (!checkDate(date)) {
            return 0L;
        }
        try {
            return simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    private static boolean checkDate(String date) {
        if (date == null) {
            return false;
        }
        return date.matches(DATE_REG);
    }


    public static String dateToHexString() {
        Calendar cal = Calendar.getInstance();
        String year = Integer.toHexString(cal.get(Calendar.YEAR) % 100);
        String month = Integer.toHexString(cal.get(Calendar.MONTH) % 100);
        String day = Integer.toHexString(cal.get(Calendar.DAY_OF_MONTH) % 100);
        String hour = Integer.toHexString(cal.get(Calendar.HOUR_OF_DAY) % 100);
        String minute = Integer.toHexString(cal.get(Calendar.MINUTE) % 100);
        String second = Integer.toHexString(cal.get(Calendar.SECOND) % 100);

        return year + month + day + hour + minute + second;
    }
}
