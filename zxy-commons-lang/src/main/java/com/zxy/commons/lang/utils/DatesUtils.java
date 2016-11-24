/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.zxy.commons.lang.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 * 
 * <pre>
 * example：
 * 1. 当前日期转yyyy-MM-dd HH:mm:ss格式字符串：
 *    DatesUtils.YYMMDDHHMMSS.toString();
 * 2. yyyy-MM-dd HH:mm:ss格式字符串转日期：
 *    DatesUtils.YYMMDDHHMMSS.toDate("2016-06-21 13:00:00");
 * </pre>
 * <p>
 * <a href="DatesUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public enum DatesUtils {

    UTC("yyyyMMdd'T'HHmmssZ"), 
    UTCNOZ("yyyyMMdd'T'HHmmss"),
    
    /**
     * EEE, dd MMM yyyy HH:mm:ss Z
     */
    EEEDMMMYYYYHHMMSSZ("EEE, dd MMM yyyy HH:mm:ss Z"),
    
    /**
     * yyyyMM
     * <p>比如：201601
     */
    YM("yyyyMM"), 
    
    /**
     * yyyyMMdd
     * <p>比如：20160101
     */
    YMD("yyyyMMdd"), 
    
    /**
     * yyyyMMddHH
     * <p>比如：2016010123
     */
    YMDH("yyyyMMddHH"),
    
    /**
     * yyyy-MM
     * <p>比如：2016-01
     */
    YYMM("yyyy-MM"),

    /**
     * yyyy-MM-dd
     * <p>比如：2016-01-01
     */
    YYMMDD("yyyy-MM-dd"), 
    
    /**
     * yyyy-MM-dd HH:mm:ss
     * <p>比如：2016-01-01 23:01:01
     */
    YYMMDDHHMMSS("yyyy-MM-dd HH:mm:ss"),
    
    /**
     * yyyy/MM
     * <p>比如：2016/01
     */
    YYMM2("yyyy/MM"),

    /**
     * yyyy/MM/dd
     * <p>比如：2016/01/01
     */
    YYMMDD2("yyyy/MM/dd"), 
    
    /**
     * yyyy/MM/dd HH:mm:ss
     * <p>比如：2016/01/01 23:01:01
     */
    YYMMDDHHMMSS2("yyyy/MM/dd HH:mm:ss");

    private String dateType;
    private Logger log = LoggerFactory.getLogger(DatesUtils.class);

    DatesUtils(String dateType) {
        this.dateType = dateType;
    }

    /**
     * 返回日期类型
     * 
     * @return 日期类型
    */
    public String getDateType() {
        return dateType;
    }

    /**
     * 转换当前日期为字符串
     * 
     * @return 日期字符串
     */
    public String toString() {
        return toString(new Date());
    }

    /**
     * 转换指定日期为字符串
     * 
     * @param date 日期
     * @return 日期字符串
     */
    public String toString(Date date) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(this.getDateType(), Locale.ENGLISH);
            df.applyPattern(this.getDateType());
            return df.format(date);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * 转换当前日期为另外格式日期
     * 
     * @return Date
     */
    public Date toDate() {
        return toDate(new Date());
    }

    /**
     * 转换指定日期为另外格式日期
     * 
     * @param date 日期
     * @return Date
     */
    public Date toDate(Date date) {
        return toDate(this.toString(date));
    }

    /**
     * 转换指定字符串为日期
     * 
     * @param dateString 日期字符串
     * @return 日期
     */
    public Date toDate(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(this.getDateType(), Locale.CHINA);
        df.applyPattern(this.getDateType());
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return date;
    }

    /**
     * 转换指定字符串为日期
     * 
     * @param dateString 日期字符串
     * @param locale locale
     * @return 日期
     */
    public Date toDate(String dateString, Locale locale) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(this.getDateType(), locale);
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return date;
    }

    /**
     * 获取今年的第几周
     * 
     * @return 今年的第几周
     */
    public static String getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 获取指定年的年周
     * 
     * @param date date
     * @return 年周
     */
    public static String getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        return new StringBuilder("").append(year).append(week).toString();
    }

    /**
     * UTC日期转换
     * 
     * @param dateString dateString
     * @return Date
     */
    public static Date getUTCDate(final String dateString) {
        if (dateString.endsWith("Z")) {
            return DatesUtils.UTC.toDate(dateString.replace("Z", " UTC"));
        } else {
            return DatesUtils.UTCNOZ.toDate(dateString);
        }
    }

    /**
     * 将字符串转换成日期,匹配DateType中的类型，直到转换成功为止
     * 
     * @param dateString dateString
     * @return Date
     */
    @SuppressWarnings("PMD.EmptyCatchBlock")
    public static Date getDate(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        Date date = null;
        SimpleDateFormat df = new SimpleDateFormat();
        for (DatesUtils dateType : DatesUtils.values()) {
            try {
                String pattern = dateType.getDateType();
                df.applyPattern(pattern);
                date = df.parse(dateString);
                return date;
            } catch (ParseException ex) {
                // ex.printStackTrace();
            }
        }
        return date;
    }
}
