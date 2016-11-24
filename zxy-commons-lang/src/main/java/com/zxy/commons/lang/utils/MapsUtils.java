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

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Map工具类
 * 
 * <p>
 * <a href="MapsUtils.java"><i>View Source</i></a>
 * </p>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class MapsUtils {
    private MapsUtils() {
    }

    /**
     * 判断map是否为空（包括null和空列表）
     * 
     * @param map map对象
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断map是否为非空（包括null和空列表）
     * 
     * @param map map对象
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 转换map为对象，通过{@link BeanUtils#populate(Object, Map)}进行转换
     * 
     * @param map map集合
     * @param classes 指定对象
     * @return 转换后的对象
     * @throws IllegalAccessException IllegalAccessException
     * @throws InstantiationException InstantiationException
     * @throws InvocationTargetException InvocationTargetException
     * @see BeanUtils#populate(Object, Map)
     */
    public static Object convertObject(Map<String, Object> map, Class<?> classes)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Object object = null;
        object = classes.newInstance();
        BeanUtils.populate(object, map);
        return object;
    }

    /**
     * 转换对象为map集合，通过{@link BeanUtils#describe(Object)}进行转换
     * 
     * @param obj 对象
     * @return map集合
     * @throws IllegalAccessException IllegalAccessException
     * @throws InvocationTargetException InvocationTargetException
     * @throws NoSuchMethodException NoSuchMethodException
     * @see BeanUtils#describe(Object)
     */
    public static Map<?, ?> convertMap(Object obj)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<?, ?> map = BeanUtils.describe(obj);
        // 会默认生成key:class,去除
        map.remove("class");

        return map;
    }
}
