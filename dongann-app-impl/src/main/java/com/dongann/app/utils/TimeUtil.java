package com.dongann.app.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    // 短日期格式
    public static String DATE_FORMAT = "yyyy-MM-dd";

    public static String date(String fmt) {
        return new SimpleDateFormat(fmt).format(new Date());
    }

    public static String date(String fmt, long t) {
        return new SimpleDateFormat(fmt).format(new Date(t));
    }

    public static String date8() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static String date8(Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    public static String date8(Timestamp date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    public static String time6() {
        return new SimpleDateFormat("HHmmss").format(new Date());
    }

    public static String time6(Date date) {
        return new SimpleDateFormat("HHmmss").format(date);
    }

    public static String time6(Timestamp date) {
        return new SimpleDateFormat("HHmmss").format(date);
    }

    public static String datetime14() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String datetime14(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    public static String datetime14(long t) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(t));
    }

    public static String calcMonth(String month6, int m) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(month6.substring(0, 4)),
            Integer.parseInt(month6.substring(4, 6)) - 1, 1);
        cal.add(Calendar.MONTH, m);
        return new SimpleDateFormat("yyyyMM").format(cal.getTime());
    }

    public static String calcDay(String day8, int d) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(day8.substring(0, 4)), Integer.parseInt(day8.substring(4, 6)) - 1,
            Integer.parseInt(day8.substring(6, 8)));
        cal.add(Calendar.DATE, d);
        return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
    }

    public static String calcSecond(String time14, int s) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(time14.substring(0, 4)),
            Integer.parseInt(time14.substring(4, 6)) - 1, Integer.parseInt(time14.substring(6, 8)),
            Integer.parseInt(time14.substring(8, 10)), Integer.parseInt(time14.substring(10, 12)),
            Integer.parseInt(time14.substring(12, 14)));
        cal.add(Calendar.SECOND, s);
        return new SimpleDateFormat("yyyyMMddHHmmss").format(cal.getTime());
    }

    public static long toMilliSec(String time14) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(time14.substring(0, 4)),
            Integer.parseInt(time14.substring(4, 6)) - 1, Integer.parseInt(time14.substring(6, 8)),
            Integer.parseInt(time14.substring(8, 10)), Integer.parseInt(time14.substring(10, 12)),
            Integer.parseInt(time14.substring(12, 14)));
        return cal.getTimeInMillis();
    }

    public static int getActualMaximum(String day8, int field) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(day8.substring(0, 4)), Integer.parseInt(day8.substring(4, 6)) - 1,
            Integer.parseInt(day8.substring(6, 8)));
        return cal.getActualMaximum(field);
    }

    public static int getActualMinimum(String day8, int field) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(day8.substring(0, 4)), Integer.parseInt(day8.substring(4, 6)) - 1,
            Integer.parseInt(day8.substring(6, 8)));
        return cal.getActualMinimum(field);
    }

    /**
     * 获取当月第一天日期
     * @return
     */
    public static String getMonthStart() {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        String dateStr = df.format(new Date());
        dateStr = dateStr.substring(0, 8) + "01";
        return dateStr;
    }

    /**
     * 
     * 得到系统时间年份
     * 
     * @return
     */
    public static String getYear() {
        SimpleDateFormat yyyyDf = new SimpleDateFormat("yyyy");
        return yyyyDf.format(new Date());
    }

    /**
     * 处理银行的checkDate没有年份的情况
     * 
     * @param v
     * @return
     */
    public static String formatBankCheckDate(String v) {
        if (v.length() == 4) {
            String yyyy = getYear();
            String stldate = yyyy + v;
            try {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

                long slttime = fmt.parse(stldate).getTime();

                long currtime = System.currentTimeMillis();

                long dis = slttime - currtime;

                long day1 = 24 * 3600 * 1000;

                if (Math.abs(dis) >= day1) {

                    if (slttime < currtime) {

                        yyyy = String.valueOf(Integer.parseInt(yyyy) + 1);

                        stldate = yyyy + v;

                    } else {

                        yyyy = String.valueOf(Integer.parseInt(yyyy) - 1);

                        stldate = yyyy + v;

                    }

                }

                return stldate;
            } catch (Exception e) {

            }
        }
        return v;

    }

    /**
     * 
     * description: 获得当天是当年的第几周
     * @return
     */
    public static int getWeekIndex() {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 
     * description: 解析日期 added by 任水
     * @param fmt
     * @param dateStr
     * @return
     */
    public static Date parseDate(String fmt, String dateStr) throws RuntimeException {
        SimpleDateFormat formatter = new SimpleDateFormat(fmt);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析异常[fmt=" + fmt + ", dateStr=" + dateStr + "]", e);
        }
    }

    /**
     * 
     * description: 在now基础上增加amount个月
     * @param now
     * @param amount
     * @return
     */
    public static Date addMonth(Date now, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MONTH, amount);
        return cal.getTime();
    }

    /**
     * 
     * description: 在now基础上增加amount个年
     * @param now
     * @param amount
     * @return
     */
    public static Date addYear(Date now, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.YEAR, amount);
        return cal.getTime();
    }

    /**
     * 
     * description: 获取date所在月的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int maxDayOfCurMon = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, maxDayOfCurMon);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 
     * description: 获取today的前一天
     * @param today
     * @return
     */
    public static Date getYesterday(Date today) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return cal.getTime();
    }

    //计算日期间隔天数
    public static long getIntervalDays(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return -1;
        }

        return (long) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24) + 0.5);
    }

    /**
     * 
     * description: 格式化日期
     * @param date
     * @param fmt
     * @return
     */
    public static String formatDate(Date date, String fmt) {
        return new SimpleDateFormat(fmt).format(date);
    }

    public static Date addMin(Date now, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MINUTE, amount);
        return cal.getTime();
    }

    /**
     *
     * description: 天数间隔日期
     * @param today
     * @return
     */
    public static Date addDay(Date today, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    public static Date addDayAndSetHourMinuteSecond(Date today, int days, int hour, int minute,
                                                    int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_YEAR, days);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static long getTimeLong(Date dt) {
        long nowLong = dt.getTime();
        return nowLong;
    }

    /**
     * 获取两个时间戳的差（指定天，小时，分钟，秒）,返回-1表示不支持类型
     * @param unit only support: DAYS, HOURS, MINUTES, SECONDS
     * @param time1 such as somedate.getTime()
     * @param time2 such as somedate.getTime()
     * @return max(time1, time2) - min(time1, time2) by refer TimeUnit, result always great than zero,
     * if result == -1 that timeUnit is not support
     */
    public static long timeDiff(TimeUnit unit, long time1, long time2) {
        long result = -1;
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        if (TimeUnit.DAYS.equals(unit)) {
            result = diff / (1000 * 60 * 60 * 24);
        } else if (TimeUnit.HOURS.equals(unit)) {
            result = diff / (1000 * 60 * 60);
        } else if (TimeUnit.MINUTES.equals(unit)) {
            result = diff / (1000 * 60);
        } else if (TimeUnit.SECONDS.equals(unit)) {
            result = diff / (1000);
        } else if (TimeUnit.MILLISECONDS.equals(unit)) {
            result = diff;
        }

        return result;
    }

    /**
     * 获取上周时间差[上周周一，上周周天]
     * @return
     */
    public static String[] getLastWeekInterval() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setFirstDayOfWeek(Calendar.MONDAY);
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            dayOfWeek = 8;
        }
        calendar1.add(Calendar.DATE, -7);
        calendar1.add(Calendar.DATE, calendar1.getFirstDayOfWeek() - dayOfWeek);
        String lastBeginDate = sdf.format(calendar1.getTime());
        calendar1.add(Calendar.DATE, 6);
        String lastEndDate = sdf.format(calendar1.getTime());

        String[] result = new String[2];
        result[0] = lastBeginDate;
        result[1] = lastEndDate;
        return result;
    }
}