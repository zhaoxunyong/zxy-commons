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
package com.zxy.commons.pool.objectpool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.zxy.commons.pool.PoolConfig;

/** 
 * 缓存池配置
 * 
 * <p>
 * <a href="ObjectPoolConfig.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
 */
public class ObjectPoolConfig extends GenericObjectPoolConfig {
//	private final byte whenExhaustedAction;
    
    public ObjectPoolConfig(PoolConfig conf){
        this.setMaxIdle(conf.getMaxIdle());
        this.setMinIdle(conf.getMinIdle());
        this.setMaxTotal(conf.getMaxTotal());
        this.setMaxWaitMillis(conf.getMaxWait());
//        poolConfig.setWhenExhaustedAction(FastdfsPoolConfigurator.getWhenExhaustedAction());
        this.setTimeBetweenEvictionRunsMillis(conf.getTimeBetweenEvictionRunsMillis());
        this.setMinEvictableIdleTimeMillis(conf.getMinEvictableIdleTimeMillis());
        this.setTestWhileIdle(conf.isTestWhileIdle());
        this.setTestOnBorrow(conf.isTestOnBorrow());
//        config.setMaxTotalPerKey(5); //每个key的最大
//        config.setMinIdlePerKey(0);
        this.setNumTestsPerEvictionRun(-1); // always test all idle objects
        this.setBlockWhenExhausted(true);
        this.setTestOnReturn(false);
        
//        this.whenExhaustedAction = conf.whenExhaustedAction;
    }

//	public byte getWhenExhaustedAction() {
//		return whenExhaustedAction;
//	}
}
