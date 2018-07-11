package com.dongann.app.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoq on 2018/3/9.
 */

public class StringUtil {
    /**
     * 去掉两端空格
     *
     * @param s
     * @return
     */
    public static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    /**
     * 字符串为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return "".equals(trim(s)) ? true : false;
    }

    /**
     * 字符串非空
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * 将key全部转换为小写, key去掉空格
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> convertLowerKeyMap(Map<String, Object> obj) {
        Map<String, Object> lowerMap = new HashMap<String, Object>();
        if (obj == null || obj.isEmpty()) {
            return lowerMap;
        }
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            lowerMap.put(key != null ? key.toLowerCase().trim() : key, value);
        }
        return lowerMap;
    }

    /**
     * 将sourceMap按照clazz的成员变量名称作为key重新生成Map
     *
     * @param clazz
     * @param sourceMap
     * @return
     * @throws Exception
     */
    public static Map<String, Object> convertMapKeyByObject(Class clazz, Map<String, Object> sourceMap) throws Exception {
        Map<String, Object> rstMap = new HashMap<String, Object>();
        if (clazz != null && sourceMap != null) {
            Field[] f = clazz.getDeclaredFields();
            for (Field fie : f) {
                String fn = fie.getName();
                for (Map.Entry<String, Object> entry : sourceMap.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key != null && fn.equalsIgnoreCase(key.trim())) {
                        rstMap.put(fn, value);
                        break;
                    }
                }
            }
        }

        return rstMap;
    }

    /**
     * 获取错误栈信息
     *
     * @param throwable
     * @param lines     打印行数
     * @return
     */
    public static String getErrorStackTrace(Throwable throwable, Integer lines) {
        String error = "";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        BufferedReader br = null;
        try {
            throwable.printStackTrace(pw);
            error = sw.toString();
            if (lines != null && lines > 0) {
                br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(error.getBytes(Charset.forName("utf-8"))),
                        Charset.forName("utf-8")));
                String line;
                StringBuffer strbuf = new StringBuffer("");
                int idx = 0;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().equals("")) {
                        strbuf.append(line + "; ");
                        idx++;
                        if (idx == lines) {
                            break;
                        }
                    }
                }

                error = strbuf.toString();

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sw.close();
                pw.close();
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return error;
    }


    /**
     * 0左补齐不足长度
     *
     * @param length 长度
     * @param str    数字
     * @return
     */
    public static String lpad(int length, String str) {
        str = trim(str);
        int strLen = str.length();
        if (strLen == length) {
            return str;
        } else if (strLen < length) {
            int temp = length - strLen;
            String tem = "";
            for (int i = 0; i < temp; i++) {
                tem = tem + "0";
            }
            return tem + str;
        } else {
            return str.substring(0, length);
        }

    }

    /**
     * 右截取字符串
     *
     * @param str
     * @param length
     * @return
     */
    public static String rsub(String str, int length) {
        str = trim(str);
        if (str.length() < (length + 1)) {//当订单量大于100万时截取
            return str;
        }
        str = str.substring(str.length() - length, str.length());
        return str;
    }
    /**
     * 如果为null，转换为""
     * @param value
     * @return
     */
    public static String nullSafeString(String value) {
        return value == null ? "" : value;
    }

    /**
     * 确保存入数据库的string值不会引起数据库报错。
     * <p>
     * 1. 数据库不允许为null，value为nul时返回""；<br />
     * 2. 超过最大长度时截断字符串。
     * @param value 要存入数据库的字符串值。
     * @param nullable 是否允许为null。
     * @param maxLength 最大长度。
     * @return
     */
    public static String dbSafeString(String value, boolean nullable, int maxLength) {
        if (value == null) {
            if (nullable)
                return null;
            return nullSafeString(value);
        }
        if (value.length() > maxLength)
            return value.substring(0, maxLength);
        return value;
    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {

    }

}
