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

import org.apache.commons.lang.StringUtils;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;

/**
 * memcache工厂类
 * 
 * <p>
 * <a href="MemcacheFactory.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class MemcacheFactory {
    private MemcacheFactory() {
    }

    /**
     * Memcached factory builder
     * 
     * <p>
     * <a href="MemcachedFactory.java"><i>View Source</i></a>
     * 
     * @author zhaoxunyong@qq.com
     * @version 1.0
     * @since 1.0
     */
    final static class MemcachedFactoryBuilder {
        @SuppressWarnings("PMD")
        final static net.rubyeye.xmemcached.MemcachedClient INSTANCE = getObjectJava();
    }

    private static MemcachedClient getObjectJava() {
        String servers = MemcachePropUtils.getServers();
        if (StringUtils.isBlank(servers)) {
            throw new AssertionError("Parameter servers must not be empty.");
        }

        MemcachedClientBuilder builder = new XMemcachedClientBuilder(servers);
        builder.setConnectionPoolSize(MemcachePropUtils.getConnectPoolSize());
        builder.setConnectTimeout(MemcachePropUtils.getInstance().getLong("connectTimeout"));
        try {
            return builder.build();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public static MemcacheClient getMemcacheClient() {
        return MemcacheClient.getInstance();
    }

    /**
     * 关闭memcached服务
     * 
     * @throws IOException IOException
     */
    @SuppressWarnings("PMD.EmptyCatchBlock")
    public static void shutdown() throws IOException {
        try {
            MemcachedFactoryBuilder.INSTANCE.shutdown();
        } catch (Exception e) {
            // do nothing
        }
    }
}
