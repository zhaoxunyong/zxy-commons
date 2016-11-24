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

import com.zxy.commons.memcache.MemcacheFactory.MemcachedFactoryBuilder;

import net.rubyeye.xmemcached.MemcachedClient;

/**
 * 获取Memcached连接
 * 
 * <p>
 * <a href="MemcacheClient.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class MemcacheClient extends AbstractMemcacheClient {
    private final static class MemcachedUtilsBuilder {
        @SuppressWarnings("PMD.AccessorClassGeneration")
        private final static MemcacheClient INSTANCE = new MemcacheClient();
    }
    
    private MemcacheClient(){
        super();
    }
    
    @Override
    protected MemcachedClient getClient() {
        return MemcachedFactoryBuilder.INSTANCE;
    }
    
    @SuppressWarnings("PMD.DefaultPackage")
    static MemcacheClient getInstance(){
        return MemcachedUtilsBuilder.INSTANCE;
    }
    
}
