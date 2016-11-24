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

import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.zxy.commons.lang.callback.ModelDtoMapping;

/**
 * Collection工具类
 * 
 * <p>
 * <a href="CollectionsUtils.java"><i>View Source</i></a>
 * </p>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class CollectionsUtils {

    private CollectionsUtils() {
    }

    /**
     * 判断列表是否为空（包括null和空列表）
     * 
     * @param collection Collection对象
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断列表是否为非空（包括null和空列表）
     * 
     * @param collection Collection对象
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断集合中是否包含指定的字符串
     * 
     * @param collection 集合对象
     * @param one 指定的字符串
     * @return 是否包含
     */
    public static boolean contains(Collection<String> collection, String one) {
        return collection != null && collection.contains(one);
    }

    /**
     * Integer集合转为Integer数组
     * 
     * @param values Collection集合
     * @return int[]
     */
    public static Integer[] int2Array(Collection<Integer> values) {
        if (isEmpty(values)) {
            return null;
        }
        return values.toArray(new Integer[values.size()]);
    }

    /**
     * Float集合转为Float数组
     * 
     * @param values Collection集合
     * @return Float[]
     */
    public static Float[] float2Array(Collection<Float> values) {
        if (isEmpty(values)) {
            return null;
        }
        return values.toArray(new Float[values.size()]);
    }
    
    /**
     * Double集合转为Double数组
     * 
     * @param values Collection集合
     * @return Double[]
     */
    public static Double[] double2Array(Collection<Double> values) {
        if (isEmpty(values)) {
            return null;
        }
        return values.toArray(new Double[values.size()]);
    }
    
    /**
     * Byte集合转为Byte数组
     * 
     * @param values Collection集合
     * @return Byte[]
     */
    public static Byte[] byte2Array(Collection<Byte> values) {
        if (isEmpty(values)) {
            return null;
        }
        return values.toArray(new Byte[values.size()]);
    }

    /**
     * Long集合转为Long数组
     * 
     * @param values Collection集合
     * @return long[]
     */
    public static Long[] long2Array(Collection<Long> values) {
        if (isEmpty(values)) {
            return null;
        }
        return values.toArray(new Long[values.size()]);
    }

    /**
     * 将集合转化为数组
     * 
     * @param values collection字符串集合
     * @return 字符串数组
     */
    public static String[] string2Array(Collection<String> values) {
        if (isEmpty(values)) {
            return null;
        }
        return values.toArray(new String[values.size()]);
    }

    /**
     * 集合截取
     * 
     * @param collection collection集合
     * @param offset offset
     * @param limit limit
     * @return 截取后的集合
     */
    @SuppressWarnings("PMD.AvoidDeeplyNestedIfStmts")
    public static List<String> subStringList(List<String> collection, int offset, int limit) {
        if (collection != null && !(collection.isEmpty())) {
            if (offset >= collection.size()) {
                return collection;
            }

            if (limit > 0) {
                int toIndex = offset + limit;
                if (toIndex > collection.size()) {
                    toIndex = collection.size();
                }
                return collection.subList(offset, toIndex);
            }
        }
        return collection;
    }

    /**
     * 将List转换为String，以separator分割
     * 
     * @param sources 字符串集合
     * @param separator 需要分隔的字符串
     * @return 以separator分割的字符串
     */
    public static String toString(Collection<?> sources, String separator) {
        return Joiner.on(separator).join(sources).trim();
    }
    
    /**
     * model集合转换为dto集合
     * 
     * @param <M> model class type
     * @param <D> dto class type
     * @param models model集合
     * @param modelDtoMapping modelDtoMapping回调
     * @return dto集合
    */
    public static <M, D> List<D> models2dtos(List<M> models, ModelDtoMapping<M, D> modelDtoMapping) {
        return FluentIterable.from(models).transform(new Function<M, D>() {

            @Override
            public D apply(M model) {
                return modelDtoMapping.transform(model);
            }
        }).toList();
    }

    /**
     * dto集合转换为model集合
     * 
     * @param <D> dto class type
     * @param <M> model class type
     * @param dtos dtos集合
     * @param modelDtoMapping modelDtoMapping回调
     * @return model集合
    */
    public static <D, M> List<M> dtos2models(List<D> dtos, ModelDtoMapping<D, M> modelDtoMapping) {
        return FluentIterable.from(dtos).transform(new Function<D, M>() {

            @Override
            public M apply(D dtos) {
                return modelDtoMapping.transform(dtos);
            }
        }).toList();
    }
}
