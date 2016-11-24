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

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * Redis cluster工厂类
 * 
 * <p>
 * <a href="RedisClusterFactory.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class RedisClusterFactory {
    private final JedisCluster jedisCluster;
    
    private static final class RedisClusterFactoryBuilder {
        private static final RedisClusterFactory INSTANCE = new RedisClusterFactory();
    }

    public static RedisClusterFactory getInstance() {
        return RedisClusterFactoryBuilder.INSTANCE;
    }

    private RedisClusterFactory() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(RedisPropUtils.getMaxIdle());
        poolConfig.setMinIdle(RedisPropUtils.getMinIdle());
        poolConfig.setMaxTotal(RedisPropUtils.getMaxTotal());
        poolConfig.setMaxWaitMillis(RedisPropUtils.getMaxWaitMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(RedisPropUtils.getTimeBetweenEvictionRunsMillis());
        poolConfig.setMinEvictableIdleTimeMillis(RedisPropUtils.getMinEvictableIdleTimeMillis());
        poolConfig.setTestWhileIdle(RedisPropUtils.getTestWhileIdle());
        poolConfig.setTestOnBorrow(RedisPropUtils.getTestOnBorrow());

        int timeout = RedisPropUtils.getTimeout();
        Set<HostAndPort> haps = parseHostAndPort();
        int maxRedirections = RedisPropUtils.getMaxRedirections();
        if (maxRedirections <= 0) {
            maxRedirections = 5;
        }
        jedisCluster = new JedisCluster(haps, timeout, maxRedirections, poolConfig);
    }

    private static Set<HostAndPort> parseHostAndPort() {
        Pattern pattern = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
        Set<HostAndPort> haps = new HashSet<HostAndPort>();
        Set<String> addressConfigs = RedisPropUtils.getServers();
        for (String addressConfig : addressConfigs) {
            boolean isIpPort = pattern.matcher(addressConfig).matches();
            if (!isIpPort) {
                throw new IllegalArgumentException("ip或 port不合法!");
            }
            String[] ipAndPort = addressConfig.split(":");

            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            HostAndPort hap = new HostAndPort(ipAndPort[0].trim(), Integer.parseInt(ipAndPort[1].trim()));
            haps.add(hap);
        }
        return haps;
    }

    /**
     * @return the jedisCluster
     */
    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }
}
