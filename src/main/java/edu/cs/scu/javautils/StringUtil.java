package edu.cs.scu.javautils;

import org.apache.log4j.Logger;

/**
 * 字符串工具类
 * <p>
 * Created by Wang Han on 2017/3/29 14:46.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han SCU. All Rights Reserved.
 *
 * @author Wang Han
 */
public class StringUtil {

    // 得到log记录器
    private static final Logger logger = Logger.getLogger(DateUtil.class);

    /**
     * 从字符串中提取指定的字段
     *
     * @param str       字符串
     * @param delimiter 分隔符
     * @param field     指定的字段
     * @return
     */
    public static synchronized String getFieldFromConcatString(String str, String delimiter, String field) {

        try {
            String[] fileds = str.split(delimiter);
            for (String filed : fileds) {
                if (filed.split("=").length == 2) {
                    String fieldName = filed.split("=")[0];
                    String fieldValue = filed.split("=")[1];
                    if (fieldName.equals(field)) {
                        return fieldValue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getStackTrace());
        }

        return null;
    }


    /**
     * 改变字符串中指定的字段对应的值
     *
     * @param str           字符串
     * @param delimiter     分隔符
     * @param field         字段
     * @param newfiledValue 新值
     * @return String
     */
    public static synchronized String setFieldInConcatString(String str, String delimiter, String field, String newfiledValue) {

        String[] fields = str.split(delimiter);
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].split("=")[0];
            if (fieldName.equals(field)) {
                String concatField = fieldName + "=" + newfiledValue;
                fields[i] = concatField;
                break;
            }
        }

        StringBuffer buffer = new StringBuffer("");

        for (int i = 0; i < fields.length; i++) {
            buffer.append(fields[i]);
            if (i < fields.length - 1) {
                buffer.append("|");
            }
        }

        return buffer.toString();
    }
}