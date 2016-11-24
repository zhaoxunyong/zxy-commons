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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 对象工具类
 * 
 * <p>
 * <a href="ObjectsUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class ObjectsUtils {
    private ObjectsUtils() {
    }

    /**
     * 判断对象是否为空
     * 
     * @param obj 对象
     * @return 是否为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj.toString().length() == 0) {
            return true;
        }
        return StringUtils.isEmpty(obj.toString());
    }

    /**
     * 格式化对象为字符串
     * 
     * @param object 对象
     * @return 格式化后的字符串
     */
    public static String toString(Object object) {
        return toString(object, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * 通过指定的格式，格式化对象为字符串
     * 
     * @param obj 对象
     * @param toStringStyle 指定的格式，
     *                      {@code ToStringStyle.SIMPLE_STYLE }
     *                      {@code ToStringStyle.SHORT_PREFIX_STYLE }
     *                      {@code ToStringStyle.MULTI_LINE_STYLE }
     * @return 格式化后的字符串
     * @see ToStringStyle
     */
    public static String toString(Object obj, ToStringStyle toStringStyle) {
        if (obj == null) {
            return null;
        }
        if ((obj instanceof Collection)) {
            Collection<?> cs = (Collection<?>) obj;
            List<String> returnCs = new ArrayList<>();
            for (Object c : cs) {
                returnCs.add(ReflectionToStringBuilder.toString(c, toStringStyle));
            }
            return returnCs.toString();
        }
        if ((obj instanceof Map)) {
            Map<?, ?> maps = (Map<?, ?>) obj;
            Map<String, String> returnMaps = new HashMap<>();
            for (Map.Entry<?, ?> entry : maps.entrySet()) {
                String key = entry.getKey().toString();
                String value = ReflectionToStringBuilder.toString(entry.getValue(), toStringStyle);
                returnMaps.put(key, value);
            }
            return returnMaps.toString();
        }
//        return ReflectionToStringBuilder.toString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
        return ReflectionToStringBuilder.toString(obj, toStringStyle);
    }
}
