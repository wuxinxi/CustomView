package com.wxx.customview.calendar;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者：Tangren on 2017/5/14 15:50
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public class Util {

    private static final String TAG = "Util";

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getCurrentDate = new Date();


    /**
     * 判断两个日期是否为同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        boolean isSameYear = calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

    /**
     * 简单方法的判断是否是同一天
     *
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date2) {
        if (getCurrentDate.getDate() == date2.getDate()
                && getCurrentDate.getMonth() == date2.getMonth()
                && getCurrentDate.getYear() == date2.getYear())
            return true;
        return false;
    }

    /**
     * 判断两个日期是否是同月
     *
     * @param date2
     * @return
     */
    public static boolean isSameMonth(int currentPageMonth, Date date2) {
        if (currentPageMonth == date2.getMonth())
            return true;
        return false;
    }

    public static String getTime(Date date) {
        return format.format(date);
    }
}
