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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zxy.commons.lang.conf.AbstractConfigProperties;

/**
 * 读取memcached配置文件的基类
 * 
 * <p>
 * 如果需要读取配置文件时，需要继承此类
 * <p>
 * <a href="MemcachePropUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class MemcachePropUtils extends AbstractConfigProperties {
    private static final int DEFAULT_CONNECTION_POOL_SIZE = 1;

    private final static class MemcachePropUtilsBuilder {
        private final static MemcachePropUtils INSTANCE = new MemcachePropUtils();
    }

    /**
     * 得到单例的MemcachePropUtils实例
     * 
     * @return 单例的MemcachePropUtils实例
     */
    public static MemcachePropUtils getInstance() {
        return MemcachePropUtilsBuilder.INSTANCE;
    }

    @Override
    protected URL getPath() throws IOException {
        return classpath2URL("memcache.properties");
    }

    /**
     * 得到servers列表，多个用逗号分隔
     * 
     * @return servers列表
     */
    public static String getServers() {
        return getInstance().getString("servers");
    }

    /**
     * 得到权重，多个用逗号分隔
     * 
     * @return 权重列表
     */
    public static List<Integer> getWeights() {
        String weights = getInstance().getString("weights");
        if (StringUtils.isNotBlank(weights)) {
            List<Integer> weightsList = new ArrayList<Integer>();
            String[] weightsArray = weights.split("[\\s\\,]");
            for (String w : weightsArray) {
                weightsList.add(Integer.parseInt(w));
            }
            return weightsList;
        }
        return null;
    }

    /**
     * 得到连接池大小
     * 
     * @return 连接池大小
     */
    public static int getConnectPoolSize() {
        int poolSize = getInstance().getInt("connectionPoolSize");
        return poolSize <= 0 ? DEFAULT_CONNECTION_POOL_SIZE : poolSize;
    }

    /**
     * 得到超时时间，单位：毫秒
     * 
     * @return 超时时间
     */
    public static long getConnectTimeout() {
        return getInstance().getLong("connectTimeout");
    }
}
