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
package com.zxy.commons.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.SerializationUtils;

/**
 * Spring redis cache
 * 
 * see: http://hanqunfeng.iteye.com/blog/2176172
 * 
 * <p>
 * <a href="RedisCache.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@Deprecated
public class RedisCache implements Cache {
    private RedisTemplate<String, Object> redisTemplate;
    private String name;
    // 24H
    private long expires = 86400;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @param expires the expires to set
     */
    public void setExpires(long expires) {
        this.expires = expires;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public ValueWrapper get(final Object key) {
        return redisTemplate.execute(new RedisCallback<ValueWrapper>() {
            public ValueWrapper doInRedis(RedisConnection connection) throws DataAccessException {

                byte[] keyb = SerializationUtils.serialize(key);
                byte[] value = connection.get(keyb);
                if (value == null) {
                    return null;
                }
                return new SimpleValueWrapper(SerializationUtils.deserialize(value));

            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Class<T> type) {
        return (T)get(key);
    }

    @Override
    public void put(Object key, Object value) {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyb = SerializationUtils.serialize(key);
                byte[] valueb = SerializationUtils.serialize(value);
                connection.set(keyb, valueb);
                if (expires > 0) {
                    connection.expire(keyb, expires);
                }
                return 1L;
            }
        });
        
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return redisTemplate.execute(new RedisCallback<ValueWrapper>() {
            public ValueWrapper doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyb = SerializationUtils.serialize(key);
                byte[] valueb = SerializationUtils.serialize(value);
                connection.setNX(keyb, valueb);
                if (expires > 0) {
                    connection.expire(keyb, expires);
                }
                return new SimpleValueWrapper(value);
            }
        });
    }

    @Override
    public void evict(Object key) {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(SerializationUtils.serialize(key));
            }
        });
    }

    @Override
    public void clear() {
        redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }
}
