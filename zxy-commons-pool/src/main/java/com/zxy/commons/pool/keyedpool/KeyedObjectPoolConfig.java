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
package com.zxy.commons.pool.keyedpool;

import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import com.zxy.commons.pool.PoolConfig;

/** 
 * KV缓存池配置
 * 
 * <p>
 * <a href="KeyedObjectPoolConfig.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
 */
public class KeyedObjectPoolConfig extends GenericKeyedObjectPoolConfig {
    public KeyedObjectPoolConfig(PoolConfig conf){
//        this.setMaxIdle(conf.maxIdle);
//        this.setMinIdle(conf.minIdle);
        this.setMaxTotalPerKey(conf.getMaxTotalPerKey()); //每个key的最大
        this.setMinIdlePerKey(conf.getMinIdlePerKey());
        this.setMaxTotal(conf.getMaxTotal());
        this.setMaxWaitMillis(conf.getMaxWait());
//        poolConfig.setWhenExhaustedAction(FastdfsPoolConfigurator.getWhenExhaustedAction());
        this.setTimeBetweenEvictionRunsMillis(conf.getTimeBetweenEvictionRunsMillis());
        this.setMinEvictableIdleTimeMillis(conf.getMinEvictableIdleTimeMillis());
        this.setTestWhileIdle(conf.isTestWhileIdle());
        this.setTestOnBorrow(conf.isTestOnBorrow());
        this.setNumTestsPerEvictionRun(-1); // always test all idle objects
        this.setBlockWhenExhausted(true);
        this.setTestOnReturn(false);
    }
}
