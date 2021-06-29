package com.yibi.evidence.chain.util;

import cn.hutool.core.date.format.FastDateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类
 * @author yibi
 * @date 2021-06-25 12:45
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    /**yyyy/MM/dd*/
    public static final String PATH_FORMAT = "yyyy/MM/dd";
    /**yyyyMMddHHmmss*/
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /**yyyy-MM-dd HH:mm:ss*/
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

    /**
     * 时间转换为String
     * @author yibi
     * @date 2021-06-26 20:28
     * @param dateTime 待转换时间
     * @return java.lang.String
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DEFAULT_FORMATTER);
    }

    /**
     * 获取当前时间按格式yyyyMMddHHmmss转换
     * @author yibi
     * @date 2021-06-27 23:07
     * @return java.lang.String
     */
    public static String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    /**
     * 根据转换格式将当前日期格式化
     * @author yibi
     * @date 2021-06-27 23:07
     * @param format 转换格式
     * @return java.lang.String
     */
    public static String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    /**
     * 根据转换格式将指定日期格式化
     * @author yibi
     * @date 2021-06-27 23:07
     * @param format 转换格式
     * @param date 日期
     * @return java.lang.String
     */
    public static String parseDateToStr(final String format, final Date date) {
        if (date == null) {
            return null;
        }
        FastDateFormat dateFormat = FastDateFormat.getInstance(format);
        return dateFormat.format(date);
    }
}
