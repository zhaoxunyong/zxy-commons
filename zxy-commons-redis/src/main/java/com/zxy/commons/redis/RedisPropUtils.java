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

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import com.zxy.commons.lang.conf.AbstractConfigProperties;

import redis.clients.jedis.HostAndPort;

/**
 * 读取redis配置文件的基类
 * 
 * <p>
 * 如果需要读取配置文件时，需要继承此类
 * <p>
 * <a href="RedisPropUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
class RedisPropUtils extends AbstractConfigProperties {

    private final static class JedisPropUtilsBuilder {
        private final static RedisPropUtils INSTANCE = new RedisPropUtils();
    }

    /**
     * 得到单例的RedisPropUtils实例
     * 
     * @return 单例的RedisPropUtils实例
     */
    public static RedisPropUtils getInstance() {
        return JedisPropUtilsBuilder.INSTANCE;
    }

    @Override
    protected URL getPath() throws IOException {
        return classpath2URL("redis.properties");
    }

    /**
     * 得到servers列表，多个用逗号分隔
     * 
     * @return servers列表
     */
    public static Set<String> getServers() {
        String servers = JedisPropUtilsBuilder.INSTANCE.getString("servers");
        Set<String> serverses = new HashSet<String>();
        if (StringUtils.isNotBlank(servers)) {
            String[] serversArray = servers.split("[\\s\\,]");
            for (String s : serversArray) {
                serverses.add(s);
            }
        }
        return serverses;
    }

    /**
     * 得到server列表，用于非集群的redis
     * 
     * @return server列表
     */
    public static HostAndPort getServer() {
        String server = JedisPropUtilsBuilder.INSTANCE.getString("servers");
        if(server.indexOf(':') == -1) {
            throw new IllegalArgumentException("ip或 port不合法!");
        }
        String[] servers = server.split(":");
        return new HostAndPort(servers[0], Integer.parseInt(servers[1]));
    }

    /**
     * 获取超时时间，单位：毫秒
     * 
     * @return 超时时间
     */
    public static int getTimeout() {
        return JedisPropUtilsBuilder.INSTANCE.getInt("redis.timeout");
    }

    /**
     * Get max redirections
     * 
     * @return max redirections
     */
    public static int getMaxRedirections() {
        return JedisPropUtilsBuilder.INSTANCE.getInt("redis.maxRedirections");
    }

    /**
     * Get max idle
     * 
     * @return max idle
     */
    public static int getMaxIdle() {
        return JedisPropUtilsBuilder.INSTANCE.getInt("redis.maxIdle");
    }

    /**
     * Get min idle
     * 
     * @return min idle
     */
    public static int getMinIdle() {
        return JedisPropUtilsBuilder.INSTANCE.getInt("redis.minIdle");
    }

    /**
     * Get max total
     * 
     * @return max total
     */
    public static int getMaxTotal() {
        return JedisPropUtilsBuilder.INSTANCE.getInt("redis.maxTotal");
    }

    /**
     * Get max wait millis
     * 
     * @return max wait millis
     */
    public static int getMaxWaitMillis() {
        return JedisPropUtilsBuilder.INSTANCE.getInt("redis.maxWaitMillis");
    }

    /**
     * getTimeBetweenEvictionRunsMillis
     * 
     * @return timeBetweenEvictionRunsMillis
     */
    public static int getTimeBetweenEvictionRunsMillis() {
        return JedisPropUtilsBuilder.INSTANCE.getInt("redis.timeBetweenEvictionRunsMillis");
    }

    /**
     * getMinEvictableIdleTimeMillis
     * 
     * @return minEvictableIdleTimeMillis
     */
    public static int getMinEvictableIdleTimeMillis() {
        return JedisPropUtilsBuilder.INSTANCE.getInt("redis.minEvictableIdleTimeMillis");
    }

    /**
     * getTestWhileIdle
     * 
     * @return testWhileIdle
     */
    @SuppressWarnings("PMD.BooleanGetMethodName")
    public static boolean getTestWhileIdle() {
        return JedisPropUtilsBuilder.INSTANCE.getBoolean("redis.testWhileIdle");
    }

    /**
     * getTestOnBorrow
     * 
     * @return testOnBorrow
     */
    @SuppressWarnings("PMD.BooleanGetMethodName")
    public static boolean getTestOnBorrow() {
        return JedisPropUtilsBuilder.INSTANCE.getBoolean("redis.testOnBorrow");
    }
}
