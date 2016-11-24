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

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.zxy.commons.lang.constant.CommonConstant;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * String工具类
 * 
 * <p>
 * <a href="StringsUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class StringsUtils {
    private static final String DEFAULT_SPLIT = CommonConstant.DEFAULT_SPLIT;
    private static final Charset DEFAULT_CHARSET = Charsets.UTF_8;
    private static final long KB = 1024;
    private static final long MB = 1024 * 1024;
    private static final long GB = 1024 * 1024 * 1024;
    
    private StringsUtils() {}

    /**
     * 产生随机密码
     * 
     * @param len 随机密码的长度
     * @return 随机密码
     */
    public static String generatePassword(int len) {
        return RandomStringUtils.random(len, "0123456789abcdefghijklmnopqrstuvwxyz");
    }

    /**
     * 带有{@value #DEFAULT_SPLIT}的字符串转换为List集合
     * 
     * @param source 原字符串
     * @return 集合
     */
    public static List<String> toList(String source) {
        return toList(source, DEFAULT_SPLIT);
    }

    /**
     * 带有{@code separator}的字符串转换为List集合
     * 
     * @param source 原字符串
     * @param separator 分隔字符串
     * @return 集合
     */
    public static List<String> toList(String source, String separator) {
        return Splitter.on(separator).trimResults().splitToList(source);
//        List<String> result = new ArrayList<String>();
//
//        if (source != null) {
//            String[] tmps = source.split(separator);
//            for (String tmp : tmps) {
//                if (StringUtils.isNotBlank(tmp)) {
//                    result.add(tmp);
//                }
//            }
//        }
//        return result;
    }

    /**
     * 转换为List,排除相同的值
     * 
     * @param source 原字符串
     * @return List集合
     */
    public static List<String> toUniqList(String source) {
        return toUniqList(source, DEFAULT_SPLIT);
    }

    /**
     * 转换为List,排除相同的值
     * 
     * @param source 原字符串
     * @param separator 分隔字符串
     * @return List集合
     */
    public static List<String> toUniqList(String source, String separator) {
        List<String> result = new ArrayList<String>();
        if (source != null) {
            List<String> tmps = toList(source, separator);
            for (String tmp : tmps) {
                if (!result.contains(tmp)) {
                    result.add(tmp);
                }
            }
        }
        return result;
    }

    /**
     * 当内容为null时，自动转为""
     * 
     * @param str 字符串
     * @return 转换后的字符串
     */
    public static String toString(String str) {
        return StringUtils.isEmpty(str) ? "" : str;
    }

    /**
     * 构造String对象
     * 
     * @param bytes 字节数组
     * @param charset 字符编码名称
     * @return String对象
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static String toString(byte[] bytes, String charset) throws UnsupportedEncodingException {
        return new String(bytes, charset);
    }

    /**
     * 构造String对象
     * 
     * @param bytes 字节数组
     * @return String对象
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static String toString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, DEFAULT_CHARSET);
    }

    /**
     * 判断字符串是否是全英文字母
     * 
     * @param str 字符串
     * @return 是否是全英文字母
     */
    public static boolean isLetter(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!(str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') && !(str.charAt(i) >= 'a' && str.charAt(i) <= 'z')) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将空间大小转换易懂的格式（包含单位）
     * 
     * @param size 大小
     * @return 易懂的格式
     */
    public static String formatSize(long size) {
        String result = "";
        if (size >= 0 && size < KB) {
            result = size + "B";
        } else if (size >= KB && size < MB) {
            result = size / KB + "KB";
        } else if (size >= MB && size < GB) {
            result = size / MB + "MB";
        } else if (size >= GB) {
            result = size / GB + "GB";
        }
        return result;
    }

    /**
     * 移除List中的重复项
     * 
     * @param arrList 字符串集合
     * @return 去掉重复数据的字符串集合
     */
    public static List<String> removeDuplicate(List<String> arrList) {
        if (arrList == null || arrList.isEmpty()) {
            return new ArrayList<String>();
        }
        LinkedHashSet<String> hSet = new LinkedHashSet<String>(arrList);
        List<String> tmp = new ArrayList<String>(hSet.size());
        tmp.addAll(hSet);
        return tmp;
    }

    /**
     * 数组转成字符串，以逗号分隔
     * 
     * @param arr 字符串数组
     * @return 字符串
     */
    public static String array2String(String[] arr) {
        StringBuilder tmp = new StringBuilder();
        if (arr != null && arr.length > 0) {
            for (String a : arr) {
                tmp.append(DEFAULT_SPLIT).append(a);
            }
            tmp.deleteCharAt(0);
        }
        return tmp.toString();
    }

    /**
     * 将ip地址转为长整型
     * 
     * @param ip ip
     * @return 长整型
     */
    @SuppressFBWarnings("DM_BOXED_PRIMITIVE_FOR_PARSING")
    public static long ip2Long(String ip) {
        if (StringUtils.isBlank(ip)) {
            return 0L;
        }
        String[] items = ip.split("\\.");
        return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16 | Long.valueOf(items[2]) << 8
                | Long.valueOf(items[3]);
    }

    /**
     * 是否含有空值，包括null、多个空格与空值
     * 
     * @param strs 需要判断的字符串
     * @return 是否含有空值
     */
    public static boolean isAnyBlank(String... strs) {
        if (strs == null || strs.length == 0) {
            return true;
        }
        for (String str : strs) {
            if (StringUtils.isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否含有空值，包括null与空值
     * 
     * @param strs 需要判断的字符串
     * @return 是否含有空值
     */
    public static boolean isAnyEmpty(String... strs) {
        if (strs == null || strs.length == 0) {
            return true;
        }
        for (String str : strs) {
            if (StringUtils.isEmpty(str)) {
                return true;
            }
        }
        return false;
    }
}
