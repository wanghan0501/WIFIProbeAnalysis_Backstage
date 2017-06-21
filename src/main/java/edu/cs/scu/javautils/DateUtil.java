package edu.cs.scu.javautils;

import edu.cs.scu.constants.DateConstants;
import edu.cs.scu.constants.TimeConstants;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private static final SimpleDateFormat ENGLISH_TIME_FORMAT = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.ENGLISH);
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取现在时间（yyyy-MM-dd HH:mm:ss）
     *
     * @return 此刻时间
     */
    public static synchronized String getToday() {
        return TIME_FORMAT.format(new Date());
    }

    /**
     * 解析时间字符串
     *
     * @param time 时间字符串
     * @return
     */
    public static synchronized String parseTime(String time,TimeConstants timeConstants) {
        try {
            switch (timeConstants){
                case ENGLISH_TIME_FORMAT:
                    return ENGLISH_TIME_FORMAT.format(ENGLISH_TIME_FORMAT.parse(time));
                case TIME_FORMAT:
                    return TIME_FORMAT.format(ENGLISH_TIME_FORMAT.parse(time));
                case DATE_FORMAT:
                    return DATE_FORMAT.format(TIME_FORMAT.parse(time));
            }

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
    public static synchronized boolean before(String time1, String time2) {
        try {
            Date dateTime1 = TIME_FORMAT.parse(time1);
            Date dateTime2 = TIME_FORMAT.parse(time2);
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
    public static synchronized boolean after(String time1, String time2) {
        try {
            Date dateTime1 = TIME_FORMAT.parse(time1);
            Date dateTime2 = TIME_FORMAT.parse(time2);
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

