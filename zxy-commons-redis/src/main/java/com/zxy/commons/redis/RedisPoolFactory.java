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
package com.zxy.commons.redis;

import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPool;

/**
 * Redis pool工厂类
 * 
 * <p>
 * <a href="RedisPoolFactory.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
final class RedisPoolFactory {
    
    private final JedisPool jedisPool;

    private static final class RedisPoolFactoryBuilder {
        private static final RedisPoolFactory INSTANCE = new RedisPoolFactory();
    }

    public static RedisPoolFactory getInstance() {
        return RedisPoolFactoryBuilder.INSTANCE;
    }
    
    private RedisPoolFactory() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(RedisPropUtils.getMaxIdle());
        poolConfig.setMinIdle(RedisPropUtils.getMinIdle());
        poolConfig.setMaxTotal(RedisPropUtils.getMaxTotal());
        poolConfig.setMaxWaitMillis(RedisPropUtils.getMaxWaitMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(RedisPropUtils.getTimeBetweenEvictionRunsMillis());
        poolConfig.setMinEvictableIdleTimeMillis(RedisPropUtils.getMinEvictableIdleTimeMillis());
        poolConfig.setTestWhileIdle(RedisPropUtils.getTestWhileIdle());
        poolConfig.setTestOnBorrow(RedisPropUtils.getTestOnBorrow());
        
        Set<String> hosts = RedisPropUtils.getServers();
        if(hosts.size() > 1) {
            throw new IllegalArgumentException("ip或 port不合法!");
        }
        HostAndPort hostAndPort = RedisPropUtils.getServer();
        int timeout = RedisPropUtils.getTimeout();
        this.jedisPool = new JedisPool(poolConfig, hostAndPort.getHost(), hostAndPort.getPort(), timeout);
    }
    
    /**
     * @return the jedisPool
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }
}
