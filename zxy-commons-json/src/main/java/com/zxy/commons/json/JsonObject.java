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

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Map;

import com.google.common.collect.Maps;

/** 
 * Json Object
 * 
 * <p>
 * <a href="JsonObject.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class JsonObject {
    private final Map<String, Object> jsonMap;
    
    private JsonObject() {
        jsonMap = Maps.newLinkedHashMap();
    }

    private JsonObject(String mapJsonString) {
        jsonMap = JsonUtils.toMap(mapJsonString);
    }

    /**
     * Create JsonObject
     *
     * @return JsonObject
     */
    public static JsonObject create() {
        return new JsonObject();
    }

    /**
     * Create JsonObject
     *
     * @param  mapJsonString Map json string
     * @return JsonObject
     */
    public static JsonObject create(String mapJsonString) {
        return new JsonObject(mapJsonString);
    }
    
    /**
     * Put
     * 
     * @param key key
     * @param value value
     * @return JsonObject
    */
    public JsonObject put(String key, Object value) {
        if(!jsonMap.containsKey(key)) {
            jsonMap.put(key, value);
        }
        return this;
    }
    
    /**
     * Get value
     * 
     * @param key key
     * @return value
    */
    private Object get(String key) {
        return jsonMap.get(key);
    }
    
    /**
     * Get string
     * 
     * @param key key
     * @return value
     */
    public String getString(String key) {
        Object object = get(key);
        checkArgument(object instanceof String, "Object is not string instance.");
        return String.valueOf(object);
    }
    
    /**
     * Get int
     * 
     * @param key key
     * @return value
    */
    public int getInt(String key) {
        Object object = get(key);
        checkArgument(object instanceof Integer, "Object is not integer instance.");
        return Integer.parseInt(object.toString());
    }
    
    /**
     * Get byte
     * @param key key
     * @return value
    */
    public int getByte(String key) {
        Object object = get(key);
        checkArgument(object instanceof Byte, "Object is not byte instance.");
        return Byte.parseByte(object.toString());
    }
    
    /**
     * Get long
     * 
     * @param key key
     * @return value
    */
    public long getLong(String key) {
        Object object = get(key);
        checkArgument(object instanceof Long, "Object is not long instance.");
        return Long.parseLong(object.toString());
    }
    
    /**
     * Get short
     * 
     * @param key key
     * @return value
    */
    public short getShort(String key) {
        Object object = get(key);
        checkArgument(object instanceof Short, "Object is not short instance.");
        return Short.parseShort(object.toString());
    }
    
    /**
     * Get float
     * 
     * @param key key
     * @return value
    */
    public Float getFloat(String key) {
        Object object = get(key);
        checkArgument(object instanceof Float, "Object is not float instance.");
        return Float.parseFloat(object.toString());
    }
    
    /**
     * Get double
     * 
     * @param key key
     * @return value
    */
    public Double getDouble(String key) {
        Object object = get(key);
        checkArgument(object instanceof Double, "Object is not double instance.");
        return Double.parseDouble(object.toString());
    }
    
    /**
     * Get boolean
     * 
     * @param key key
     * @return value
    */
    public boolean getBoolean(String key) {
        Object object = get(key);
        checkArgument(object instanceof Boolean, "Object is not boolean instance.");
        return Boolean.parseBoolean(object.toString());
    }
    
    /**
     * Get object
     * 
     * @param key key
     * @return value
    */
    public Object getObject(String key) {
        return get(key);
    }

    /**
     * To json string
     * 
     * @return json string
    */
    public String toJSONString() {
        return toString();
    }


    /**
     * To json string
     * 
     * @return json string
    */
    public String toString() {
        return JsonUtils.toJson(jsonMap);
    }
}
