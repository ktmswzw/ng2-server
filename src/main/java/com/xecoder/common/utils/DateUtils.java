package com.xecoder.common.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class DateUtils {

    public static String DATE_PATTEN_TM = "yyyy-MM-dd HH:mm:ss";


    public static String DATE_DAY = "yyyy-MM-dd";

    public static String DATE_DAY_P = "yyyyMMdd";

    /**
     * 获取所给时间的当晚午夜时间。
     *
     * @param time 时间，毫秒数级
     * @return 午夜时间毫秒数
     */
    public static long getMidNightMS(long time) {
        Calendar midNight = Calendar.getInstance();
        midNight.setTimeInMillis(time);
        midNight.add(Calendar.DATE, 1);
        midNight.set(Calendar.HOUR_OF_DAY, 0);
        midNight.set(Calendar.SECOND, 0);
        midNight.set(Calendar.MINUTE, 0);
        midNight.set(Calendar.MILLISECOND, 0);
        return midNight.getTimeInMillis();
    }

    /**
     * 比较两个时间相差天数
     *
     * @param today
     * @param time
     * @return
     */
    public static long compareDays(long today, long time) {
        today = getTodayMS(today);
        time = getTodayMS(time);
        return (today - time) / (24 * 3600 * 1000);

    }

    public static long compareDays2(long today, long time) {
        return (today - time) / (24 * 3600 * 1000);
    }

    /**
     * 比较两个时间相差秒
     *
     * @param today
     * @param time
     * @return
     */
    public static long compareSeconds(long today, long time) {
        return (today - time) / (1000);
    }

    public static Date addDay(Date date, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, day);//把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(calendar.SECOND, 1);
        date = calendar.getTime();
        return date;
    }

    public static Date addSecond(Date date, int second) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.SECOND, second);
        date = calendar.getTime();
        return date;
    }

    public static Date addRandom(Date begin,Date end){
        Date now = new Date();
        long secondDifference = DateUtils.compareSeconds(begin.getTime(),end.getTime());
        Random randomZ = new Random();
        int finalSecond = randomZ.nextInt((int) secondDifference)+10;
        return addSecond(now,finalSecond);
    }


    public static String formatDateTime(Date date) {
        String s = DateFormatUtils.format(date, DATE_PATTEN_TM);
        return s;
    }

    /**
     * 获取当天00:00时间
     *
     * @param time 时间，毫秒数级
     * @return 时间毫秒数
     */
    public static long getTodayMS(long time) {
        return getTodayDate(time).getTime();
    }

    public static long getTodayMidNightMS() {
        return getMidNightMS(System.currentTimeMillis());
    }

    public static Date getTodayDate(long time) {
        Calendar midNight = Calendar.getInstance();
        midNight.set(Calendar.HOUR_OF_DAY, 0);
        midNight.set(Calendar.SECOND, 0);
        midNight.set(Calendar.MINUTE, 0);
        midNight.set(Calendar.MILLISECOND, 0);
        return midNight.getTime();
    }

    public static String getTodayDate() {
        DateFormat format = new SimpleDateFormat(DATE_DAY_P);
        return format.format(new Date());
    }


    public static String getDateString(Date in) {
        DateFormat format = new SimpleDateFormat(DATE_DAY_P);
        return format.format(in);
    }


    public static String getTodayDate(Date date, DateFormat format) {
        return format.format(new Date());
    }


    public static Date getDate(String date) {
        DateFormat format = new SimpleDateFormat(DATE_DAY);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getNowTimeString() {
        DateFormat format = new SimpleDateFormat("hh:mm");
        return format.format(new Date());
    }


    public static String getNowTimeString(Date date) {
        DateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    public static long stringToTime(String time) {
        DateFormat format = new SimpleDateFormat("hh:mm");
        try {
            return format.parse(time).getTime();
        } catch (Exception e) {
            return 0;
        }

    }
}
