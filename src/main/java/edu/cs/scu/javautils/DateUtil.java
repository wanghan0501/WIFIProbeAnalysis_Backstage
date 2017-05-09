package edu.cs.scu.javautils;

import edu.cs.scu.constants.DateConstants;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * <p>
 * Created by Wang Han on 2017/3/30 20:24.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */

public class DateUtil {

    // 得到log记录器
    private static final Logger logger = Logger.getLogger(DateUtil.class);
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat COMPRESSION_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 获取现在时间（yyyy-MM-dd HH:mm:ss）
     *
     * @return 此刻时间
     */
    public static synchronized String getToday() {
        return COMPRESSION_TIME_FORMAT.format(new Date());
    }

    /**
     * 解析时间字符串
     *
     * @param time 时间字符串
     * @return Date
     */
    public static synchronized Date parseTime(String time) {
        try {
            return TIME_FORMAT.parse(time);
        } catch (ParseException e) {
            logger.error(e.getStackTrace());
            System.err.println(e.getStackTrace());
        }

        return null;
    }

    /**
     * 解析时间字符串
     *
     * @param time 时间字符串
     * @return Date
     */
    public static synchronized Date parseCompressionTime(String time) {
        try {
            return COMPRESSION_TIME_FORMAT.parse(time);
        } catch (ParseException e) {
            logger.error(e.getStackTrace());
            System.err.println(e.getStackTrace());
        }

        return null;
    }

    /**
     * 判断一个时间是否在另一个时间之前
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static synchronized boolean before(String time1, String time2, String type) {
        try {
            Date dateTime1 = null;
            Date dateTime2 = null;
            switch (type) {
                case "TIME_FORMAT":
                    dateTime1 = TIME_FORMAT.parse(time1);
                    dateTime2 = TIME_FORMAT.parse(time2);
                    break;
                case "DATE_FORMAT":
                    dateTime1 = DATE_FORMAT.parse(time1);
                    dateTime2 = DATE_FORMAT.parse(time2);
                    break;
                case "COMPRESSION_TIME_FORMAT":
                    dateTime1 = COMPRESSION_TIME_FORMAT.parse(time1);
                    dateTime2 = COMPRESSION_TIME_FORMAT.parse(time2);
                    break;
                default:
                    break;
            }
            if (dateTime1.before(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.err.println(e.getMessage());
        }

        return false;
    }

    /**
     * 判断一个时间是否在另一个时间之后
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static synchronized boolean after(String time1, String time2, String type) {
        try {
            Date dateTime1 = null;
            Date dateTime2 = null;
            switch (type) {
                case "TIME_FORMAT":
                    dateTime1 = TIME_FORMAT.parse(time1);
                    dateTime2 = TIME_FORMAT.parse(time2);
                    break;
                case "DATE_FORMAT":
                    dateTime1 = DATE_FORMAT.parse(time1);
                    dateTime2 = DATE_FORMAT.parse(time2);
                    break;
                case "COMPRESSION_TIME_FORMAT":
                    dateTime1 = COMPRESSION_TIME_FORMAT.parse(time1);
                    dateTime2 = COMPRESSION_TIME_FORMAT.parse(time2);
                    break;
                default:
                    break;
            }
            if (dateTime1.after(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.err.println(e.getMessage());
        }

        return false;
    }

    /**
     * 判断一个时间是否在另一个时间之后10分钟
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static boolean intervalTenMin(String time1, String time2) {
        try {
            Date dateTime1 = TIME_FORMAT.parse(time1);
            Date dateTime2 = TIME_FORMAT.parse(time2);

            if (dateTime2.getTime() - dateTime1.getTime() >= DateConstants.TIME_PERIOD_VALUE_10m) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.err.println(e.getMessage());
        }

        return false;
    }

    /**
     * 判断一个时间是否在另一个时间之后1分钟
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static boolean intervalOneMin(String time1, String time2) {
        try {
            Date dateTime1 = TIME_FORMAT.parse(time1);
            Date dateTime2 = TIME_FORMAT.parse(time2);

            if (dateTime2.getTime() - dateTime1.getTime() >= DateConstants.TIME_PERIOD_VALUE_1m) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.err.println(e.getMessage());
        }

        return false;
    }
}

