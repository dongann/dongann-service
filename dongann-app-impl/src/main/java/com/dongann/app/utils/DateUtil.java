package com.dongann.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @FileName: DateUtil
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/6/15 11:56
 * @Version: v1.0
 * @description:
 */
public class DateUtil {

    public static final String PATTERN_STANDARD08W = "yyyyMMdd";
    public static final String PATTERN_STANDARD12W = "yyyyMMddHHmm";
    public static final String PATTERN_STANDARD14W = "yyyyMMddHHmmss";
    public static final String PATTERN_STANDARD17W = "yyyyMMddHHmmssSSS";

    public static final String PATTERN_STANDARD10H = "yyyy-MM-dd";
    public static final String PATTERN_STANDARD16H = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_STANDARD19H = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_STANDARD10X = "yyyy/MM/dd";
    public static final String PATTERN_STANDARD16X = "yyyy/MM/dd HH:mm";
    public static final String PATTERN_STANDARD19X = "yyyy/MM/dd HH:mm:ss";

    // 短日期格式
    public static String DATE_FORMAT = "yyyy-MM-dd";

    // 长日期格式
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @Title: date2String
     * @Description: 日期格式的时间转化成字符串格式的时间
     * @author YFB
     * @param date
     * @param pattern
     * @return
     */
    public static String date2String(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("timestamp null illegal");
        }
        pattern = (pattern == null || pattern.equals(""))?PATTERN_STANDARD19H:pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * @Title: string2Date
     * @Description: 字符串格式的时间转化成日期格式的时间
     * @author YFB
     * @param strDate
     * @param pattern
     * @return
     */
    public static Date string2Date(String strDate, String pattern) {
        if (strDate == null || strDate.equals("")) {
            throw new RuntimeException("strDate is null");
        }
        pattern = (pattern == null || pattern.equals(""))?PATTERN_STANDARD19H:pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * 将日期生成自定格式的字符串
     * @param budatDate
     * @param pattern
     * @return
     */
    public static String getDateStrForFormat(Date budatDate, String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        if (budatDate == null) {
            return null;
        }
        return sf.format(budatDate);
    }

    /**
     * @Title: getCurrentTime
     * @Description: 取得当前系统时间
     * @author YFB
     * @param format 格式 17位(yyyyMMddHHmmssSSS) (14位:yyyyMMddHHmmss) (12位:yyyyMMddHHmm) (8位:yyyyMMdd)
     * @return
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        String date = formatDate.format(new Date());
        return date;
    }

    /**
     * @Title: getWantDate
     * @Description: 获取想要的时间格式
     * @author YFB
     * @param dateStr
     * @param wantFormat
     * @return
     */
    public static String getWantDate(String dateStr,String wantFormat){
        if(!"".equals(dateStr)&&dateStr!=null){
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            switch(len){
                case 8:pattern = PATTERN_STANDARD08W;break;
                case 12:pattern = PATTERN_STANDARD12W;break;
                case 14:pattern = PATTERN_STANDARD14W;break;
                case 17:pattern = PATTERN_STANDARD17W;break;
                case 10:pattern = (dateStr.contains("-"))?PATTERN_STANDARD10H:PATTERN_STANDARD10X;break;
                case 16:pattern = (dateStr.contains("-"))?PATTERN_STANDARD16H:PATTERN_STANDARD16X;break;
                case 19:pattern = (dateStr.contains("-"))?PATTERN_STANDARD19H:PATTERN_STANDARD19X;break;
                default:pattern = PATTERN_STANDARD14W;break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(wantFormat);
            try {
                SimpleDateFormat sdfStr = new SimpleDateFormat(pattern);
                Date date = sdfStr.parse(dateStr);
                dateStr = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dateStr;
    }

    /**
     * @Title: getAfterTime
     * @Description: 获取该时间的几分钟之后的时间
     * @author YFB
     * @param dateStr
     * @param minute
     * @return
     */
    public static String getAfterTime(String dateStr,int minute){
        String returnStr = "";
        try {
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            switch(len){
                case 8:pattern = PATTERN_STANDARD08W;break;
                case 10:pattern = PATTERN_STANDARD10H;break;
                case 12:pattern = PATTERN_STANDARD12W;break;
                case 14:pattern = PATTERN_STANDARD14W;break;
                case 16:pattern = PATTERN_STANDARD16H;break;
                case 17:pattern = PATTERN_STANDARD17W;break;
                case 19:pattern = PATTERN_STANDARD19H;break;
                default:pattern = PATTERN_STANDARD14W;break;
            }
            SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
            Date date = null;
            date = formatDate.parse(dateStr);
            Date afterDate = new Date(date.getTime()+(60000*minute));
            returnStr = formatDate.format(afterDate);
        } catch (Exception e) {
            returnStr = dateStr;
            e.printStackTrace();
        }
        return returnStr;
    }

    /**
     * @Title: getBeforeTime
     * @Description: 获取该时间的几分钟之前的时间
     * @author YFB
     * @param dateStr
     * @param minute
     * @return
     */
    public static String getBeforeTime(String dateStr,int minute){
        String returnStr = "";
        try {
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            switch(len){
                case 8:pattern = PATTERN_STANDARD08W;break;
                case 10:pattern = PATTERN_STANDARD10H;break;
                case 12:pattern = PATTERN_STANDARD12W;break;
                case 14:pattern = PATTERN_STANDARD14W;break;
                case 16:pattern = PATTERN_STANDARD16H;break;
                case 17:pattern = PATTERN_STANDARD17W;break;
                case 19:pattern = PATTERN_STANDARD19H;break;
                default:pattern = PATTERN_STANDARD14W;break;
            }
            SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
            Date date = null;
            date = formatDate.parse(dateStr);
            Date afterDate = new Date(date.getTime()-(60000*minute));
            returnStr = formatDate.format(afterDate);
        } catch (Exception e) {
            returnStr = dateStr;
            e.printStackTrace();
        }
        return returnStr;
    }

    /**
     * 判断给定日期是否为月末的一天
     * @param date
     * @return true:是|false:不是
     */
    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前时间的下一天
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }

    public static Date getBeforeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);//-1今天的时间减一天
        date = calendar.getTime();
        return date;
    }

    public static Date getBeforeDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);//-1今天的时间减一天
        return calendar.getTime();
    }
    /**
     * 判断一个字符串是不是一个合法的日期格式
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }

    public static Date getDateStart(Date date) {

        SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat sdfTime = new SimpleDateFormat(TIME_FORMAT);
        if(date==null) {
            return null;
        }
        try {
            date= sdfTime.parse(sdfDate.format(date)+" 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateEnd(Date date) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat sdfTime = new SimpleDateFormat(TIME_FORMAT);
        if(date==null) {
            return null;
        }
        try {
            date= sdfTime.parse(sdfDate.format(date) +" 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 对日期、时间进行加、减操作。
     * <pre>
     *     DateUtil.add(date, Calendar.YEAR, -1); //date减一年
     *     DateUtil.add(date, Calendar.HOUR, -4); //date减4个小时
     *     DateUtil.add(date, Calendar.MONTH, 3); //date加3个月
     * </pre>
     *
     * @param date   日期时间。
     * @param field  执行加减操作的属性，参考{@link Calendar#YEAR}、{@link Calendar#MONTH}、{@link Calendar#HOUR}等。
     * @param amount 加减数量。
     * @return 执行加减操作后的日期、时间。
     */
    public static Date add(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 获取传入日期当前月的第一天
     *
     * @param date
     * @return
     */
    public static String getFirstDayOfMonth(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取传入日期当前月的最后一天
     *
     * @param date
     * @return
     */
    public static String getLastDayOfMonth(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return sdf.format(cal.getTime());
    }

    public static void main(String[] args){
        System.out.println(getBeforeDay());
    }


}