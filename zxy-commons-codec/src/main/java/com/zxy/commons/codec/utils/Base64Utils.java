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
package com.zxy.commons.codec.utils;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * base64工具类
 * 
 * <p>
 * <a href="Base64Utils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class Base64Utils {
    private Base64Utils() {
    }

    /**
     * 编码
     * 
     * @param source 需要编码的字符串
     * @return 编码后的字符串
     */
    public static String encode(String source) {
        return encode(source, "UTF-8");
    }

    /**
     * 编码
     * 
     * @param source 需要编码的字符串
     * @param charset 字符集
     * @return 编码后的字符串
     */
    public static String encode(String source, String charset) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        return new String(Base64.encodeBase64(source.getBytes(Charset.forName(charset))));
    }

    /**
     * 解码
     * 
     * @param base64Str 需要还原的字符串
     * @return 还原回来的字符串
     */
    public static String decode(String base64Str) {
        return decode(base64Str, "UTF-8");
    }

    /**
     * 解码
     * 
     * @param base64Str 需要还原的字符串
     * @param charset 字符集
     * @return 还原回来的字符串
     */
    public static String decode(String base64Str, String charset) {
        if (StringUtils.isBlank(base64Str)) {
            return null;
        }
        return new String(Base64.decodeBase64(base64Str.getBytes(Charset.forName(charset))));
    }
}
