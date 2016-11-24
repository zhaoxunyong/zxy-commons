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

/**
 * int工具类
 * 
 * <p>
 * <a href="IntsUtils.java"><i>View Source</i></a>
 * </p>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class IntsUtils {
    private IntsUtils() {
    }

    /**
     * Integer转换为int，避免出现空指针的错误
     * 
     * @param value Integer值
     * @return int值
     */
    public static int integer2int(Integer value) {
        return value == null ? 0 : value.intValue();
    }

    /**
     * int转字符串，当value==null转换为空字符串
     * 
     * @param value integer数值
     * @return 字符串
     */
    public static String int2String(Integer value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }
}
