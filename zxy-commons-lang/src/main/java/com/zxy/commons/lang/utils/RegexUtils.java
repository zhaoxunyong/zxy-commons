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

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 
 * <p>
 * <a href="RegexUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 * 
 */
public final class RegexUtils {
    /**
     * 正则表达式忽略大小写：{@code Pattern.CASE_INSENSITIVE}
     */
    public static final int CASE_INSENSITIVE = Pattern.CASE_INSENSITIVE;
    
    /**
     * 正则表达式启用多行模式：{@code Pattern.MULTILINE}
     */
    public static final int MULTILINE = Pattern.MULTILINE;

    private RegexUtils() {
    }

    /**
     * 模糊匹配正则表达式
     * 
     * @param regex 正则表达式
     * @param str 需要匹配的字符串
     * @return 是否匹配
     */
    public static boolean find(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).find();
    }

    /**
     * 模糊匹配正则表达式
     * 
     * @param regex 正则表达式
     * @param str 需要匹配的字符串
     * @param flags 匹配模式，包括：
     *           <p>忽略大小写：{@code RegexUtils.CASE_INSENSITIVE}，参考：{@link #CASE_INSENSITIVE}
     *           <p>多行模式：{@code RegexUtils.MULTILINE}，参考：{@link #MULTILINE}
     * @return 是否匹配
     */
    public static boolean find(String regex, String str, int flags) {
        Pattern pattern = Pattern.compile(regex, flags);
        return pattern.matcher(str).find();
    }

    /**
     * 精确匹配正则表达式
     * 
     * @param regex 正则表达式
     * @param str 需要匹配的字符串
     * @return 是否匹配
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    /**
     * 精确匹配正则表达式
     * 
     * @param regex 正则表达式
     * @param str 需要匹配的字符串
     * @param flags 匹配模式，包括：
     *           <p>忽略大小写：{@link #CASE_INSENSITIVE}
     *           <p>多行模式：{@link #MULTILINE}
     * @return 是否匹配
     */
    public static boolean match(String regex, String str, int flags) {
        Pattern pattern = Pattern.compile(regex, flags);
        return pattern.matcher(str).matches();
    }
    
    /**
     * 转换正则表达式中的特殊字符
     * 
     * @param regexp regexp
     * @return 转换后的正则表达式
     */
    public static String convertRegexp(String regexp){
        return regexp.replace("\\", "\\\\")
        .replace("$", "\\$")
        .replace("^", "\\^")
        .replace(".", "\\.")
        .replace(":", "\\:")
        .replace(",", "\\,")
        .replace("-", "\\-")
        .replace("(", "\\(")
        .replace(")", "\\)")
        .replace("[", "\\[")
        .replace("]", "\\]")
        .replace("{", "\\{")
        .replace("}", "\\}")
        .replace("*", "\\*")
        .replace("+", "\\+")
        .replace("?", "\\?")
        .replace("|", "\\|");
    }
}
