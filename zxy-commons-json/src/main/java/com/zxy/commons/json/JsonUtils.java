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
package com.zxy.commons.json;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/** 
 * Json转换工具类
 * 
 * <p>
 * <a href="JsonUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
@SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
public final class JsonUtils {
    private final ObjectMapper mapper;
    /*private static Map<Include, Function<Include, JsonUtils>> map = new HashMap<>();
    static {
        map.put(Include.NON_EMPTY, (include) -> new JsonUtils(include));
        map.put(Include.NON_NULL, JsonUtils::new);
        map.put(Include.ALWAYS, JsonUtils::new);
        map.put(Include.NON_ABSENT, JsonUtils::new);
        map.put(Include.NON_DEFAULT, JsonUtils::new);
        map.put(Include.USE_DEFAULTS, JsonUtils::new);
    }*/
    
    private final static class JsonUtilsBuilder {
        private final static JsonUtils INSTANCE_NON_EMPTY = new JsonUtils(Include.NON_EMPTY);
        private final static JsonUtils INSTANCE_NON_NULL = new JsonUtils(Include.NON_NULL);
        private final static JsonUtils INSTANCE_ALWAYS = new JsonUtils(Include.ALWAYS);
        private final static JsonUtils INSTANCE_NON_ABSENT = new JsonUtils(Include.NON_ABSENT);
        private final static JsonUtils INSTANCE_NON_DEFAULT = new JsonUtils(Include.NON_DEFAULT);
        private final static JsonUtils INSTANCE_USE_DEFAULTS = new JsonUtils(Include.USE_DEFAULTS);
    }
    
    private JsonUtils(Include include) {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        if(include != null) {
            // mapper.setSerializationInclusion(Include.NON_NULL);
            mapper.setSerializationInclusion(include);
        }
    }
    
    private static ObjectMapper getObjectMapper(Include include) {
        JsonUtils instance = null;
        switch (include) {
        case NON_EMPTY:
            instance = JsonUtilsBuilder.INSTANCE_NON_EMPTY;
            break;
        case NON_NULL:
            instance = JsonUtilsBuilder.INSTANCE_NON_NULL;
            break;
        case ALWAYS:
            instance = JsonUtilsBuilder.INSTANCE_ALWAYS;
            break;
        case NON_ABSENT:
            instance = JsonUtilsBuilder.INSTANCE_NON_ABSENT;
            break;
        case NON_DEFAULT:
            instance = JsonUtilsBuilder.INSTANCE_NON_DEFAULT;
            break;
        case USE_DEFAULTS:
            instance = JsonUtilsBuilder.INSTANCE_USE_DEFAULTS;
            break;
        default:
            instance = JsonUtilsBuilder.INSTANCE_NON_EMPTY;
            break;
        }
        return instance.mapper;
    }
    
    /**
     * 对象转换为json字符串
     * 
     * @param object 对象
     * @return json字符串
     */
    public static String toJson(Object object) {
        return toJson(Include.NON_EMPTY, object);
    }
    
    /**
     * 对象转换为json字符串
     * 
     * @param include include
     * @param object 对象
     * @return json字符串
     */
    public static String toJson(JsonInclude.Include include, Object object) {
        try {
            ObjectMapper mapper = getObjectMapper(include);
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * map转换为json字符串
     * 
     * @param <K> This is the key parameter
     * @param <V> This is the value parameter
     * @param map map对象
     * @return json字符串
     */
    public static <K, V> String toJson(Map<K, V> map) {
        return toJson(Include.NON_EMPTY, map);
    }

    /**
     * map转换为json字符串
     * 
     * @param <K> This is the key parameter
     * @param <V> This is the value parameter
     * @param include include
     * @param map map对象
     * @return json字符串
     */
    public static <K, V> String toJson(JsonInclude.Include include, Map<K, V> map) {
        try {
            ObjectMapper mapper = getObjectMapper(include);
            return mapper.writeValueAsString(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换为指定的对象
     * 
     * @param <T> This is the type parameter
     * @param jsonString json字符串
     * @param clazz 对象
     * @return 指定的对象
     */
    public static <T> T toObject(String jsonString, Class<T> clazz) {
        return toObject(Include.NON_EMPTY, jsonString, clazz);
    }

    /**
     * json字符串转换为指定的对象
     * 
     * @param <T> This is the type parameter
     * @param include include
     * @param jsonString json字符串
     * @param clazz 对象
     * @return 指定的对象
     */
    public static <T> T toObject(JsonInclude.Include include, String jsonString, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper(include);
            return mapper.readValue(jsonString, TypeFactory.defaultInstance().constructType(clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换为指定的对象List集合
     * 
     * @param <T> This is the type parameter
     * @param listJsonString list格式的json字符串
     * @param clazz 对象
     * @return 指定的对象集合
     */
    public static <T> List<T> toList(String listJsonString, Class<T> clazz) {
        return toList(Include.NON_EMPTY, listJsonString, clazz);
    }

    /**
     * json字符串转换为指定的对象List集合
     * 
     * @param <T> This is the type parameter
     * @param include include
     * @param listJsonString list格式的json字符串
     * @param clazz 对象
     * @return 指定的对象集合
     */
    public static <T> List<T> toList(JsonInclude.Include include, String listJsonString, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper(include);
            return mapper.readValue(listJsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换为指定的对象Set集合
     * 
     * @param <T> This is the type parameter
     * @param setJsonString set格式的json字符串
     * @param clazz 对象
     * @return 指定的对象集合
     */
    public static <T> Set<T> toSet(String setJsonString, Class<T> clazz) {
        return toSet(Include.NON_EMPTY, setJsonString, clazz);
    }

    /**
     * json字符串转换为指定的对象Set集合
     * 
     * @param <T> This is the type parameter
     * @param include include
     * @param setJsonString set格式的json字符串
     * @param clazz 对象
     * @return 指定的对象集合
     */
    @SuppressWarnings("PMD.LooseCoupling")
    public static <T> Set<T> toSet(JsonInclude.Include include, String setJsonString, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper(include);
            return mapper.readValue(setJsonString, TypeFactory.defaultInstance().constructCollectionType(LinkedHashSet.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换为指定的对象Map对象
     * 
     * @param mapJsonString map格式的json字符串
     * @return 指定的对象集合
     */
    public static Map<String, Object> toMap(String mapJsonString) {
        return toMap(Include.NON_EMPTY, mapJsonString);
    }

    /**
     * json字符串转换为指定的对象Map对象
     * 
     * @param include include
     * @param mapJsonString map格式的json字符串
     * @return 指定的对象集合
     */
    public static Map<String, Object> toMap(JsonInclude.Include include, String mapJsonString) {
        return toMap(include, mapJsonString, String.class, Object.class);
    }

    /**
     * json字符串转换为指定的对象Map对象
     * 
     * @param <K> This is the key parameter
     * @param <V> This is the value parameter
     * @param mapJsonString map格式的json字符串
     * @param keyClass key对象
     * @param valueClass class对象
     * @return 指定的对象集合
     */
    public static <K, V> Map<K, V> toMap(String mapJsonString, Class<K> keyClass, Class<V> valueClass) {
        return toMap(Include.NON_EMPTY, mapJsonString, keyClass, valueClass);
    }

    /**
     * json字符串转换为指定的对象Map对象
     * 
     * @param <K> This is the key parameter
     * @param <V> This is the value parameter
     * @param include include
     * @param mapJsonString map格式的json字符串
     * @param keyClass key对象
     * @param valueClass class对象
     * @return 指定的对象集合
     */
    public static <K, V> Map<K, V> toMap(JsonInclude.Include include, String mapJsonString, Class<K> keyClass, Class<V> valueClass) {
        // Map<K, V> map = mapper.readValue(mapString, new TypeReference<Map<K, V>>() {});
        try {
            ObjectMapper mapper = getObjectMapper(include);
            return mapper.readValue(mapJsonString, TypeFactory.defaultInstance().constructMapType(Map.class, keyClass, valueClass));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
