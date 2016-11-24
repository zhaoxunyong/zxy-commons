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
package com.zxy.commons.memcache;

import java.util.List;
import java.util.Map;

import com.zxy.commons.lang.exception.DatasAccessException;

import net.rubyeye.xmemcached.MemcachedClient;

/**
 * Memcached客户端操作工具类
 * 
 * <p>
 * <a href="AbstractMemcacheClient.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractMemcacheClient {

    protected abstract MemcachedClient getClient();

    /**
     * set value
     * 
     * @param key key
     * @param value value
     */
    public void set(String key, Object value) {
        set(key, 0, value);
    }

    /**
     * set value
     * 
     * @param key key
     * @param exp 过期时间，单位：秒
     * @param value value
     */
    public void set(String key, int exp, Object value) {
        try {
            getClient().set(key, exp, value);
        } catch (Exception e) {
            throw new DatasAccessException(e.getMessage(), e);
        }
    }

    /**
     * Append value
     * 
     * @param key key
     * @param value value
     */
    public void append(String key, Object value) {
        try {
            getClient().append(key, value);
        } catch (Exception e) {
            throw new DatasAccessException(e.getMessage(), e);
        }
    }
    
    /**
     * Append value
     * 
     * @param key key
     * @param timeout 超时时间，单位：秒
     * @param value value
     */
    public void append(String key, int timeout, Object value) {
        try {
            getClient().append(key, value, timeout);
        } catch (Exception e) {
            throw new DatasAccessException(e.getMessage(), e);
        }
    }

    /**
     * add value
     * 
     * @param key key
     * @param value value
     */
    public void add(String key, Object value) {
        add(key, 0, value);
    }

    /**
     * add value
     * 
     * @param key key
     * @param exp 过期时间，单位：秒
     * @param value value
     */
    public void add(String key, int exp, Object value) {
        try {
            getClient().add(key, exp, value);
        } catch (Exception e) {
            throw new DatasAccessException(e.getMessage(), e);
        }
    }

    /**
     * get value
     * 
     * @param <T> This is a type parameter
     * @param key key
     * @return value
     */
    public <T> T get(String key) {
        try {
            return getClient().get(key);
        } catch (Exception e) {
            throw new DatasAccessException(e.getMessage(), e);
        }
    }

    /**
     * delete key
     * 
     * @param key key
     */
    public void delete(String key) {
        try {
            getClient().delete(key);
        } catch (Exception e) {
            throw new DatasAccessException(e.getMessage(), e);
        }
    }

    /**
     * incr value
     * 
     * @param key key
     * @param value value
     * @return incr后的值
     */
    public long incr(String key, long value) {
        try {
            return getClient().incr(key, value);
        } catch (Exception e) {
            throw new DatasAccessException(e.getMessage(), e);
        }
    }

    /**
     * incr value
     * 
     * @param key key
     * @param delta increment delta
     * @param initValue the initial value to be added when value is not found
     * @param exp the initial vlaue expire time, in seconds. Can be up to 30
     *            days. After 30 days, is treated as a unix timestamp of an
     *            exact date.
     * @return incr后的值
     */
    public long incr(String key, long delta, long initValue, int exp) {
        try {
            return getClient().incr(key, delta, initValue, getClient().getOpTimeout(), exp);
        } catch (Exception e) {
            throw new DatasAccessException(e.getMessage(), e);
        }
    }

    /**
     * decr value
     * 
     * @param key key
     * @param value value
     * @return decr后的值
     */
    public long decr(String key, long value) {
        try {
            return getClient().decr(key, value);
        } catch (Exception e) {
            throw new DatasAccessException(e.getMessage(), e);
        }
    }

    /**
     * 批量获取数据
     * 
     * @param <T> This is a type parameter
     * @param keys keys
     * @return values
     */
    public <T> Map<String, T> getBatch(List<String> keys) {
        try {
            return getClient().get(keys);
        } catch (Exception e) {
            throw new DatasAccessException(e.getMessage(), e);
        }
    }
}
