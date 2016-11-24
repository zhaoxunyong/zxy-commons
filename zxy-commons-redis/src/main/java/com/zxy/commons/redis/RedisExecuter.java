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

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis操作工具类
 * 
 * <div>example：</div>
 * <div><img src="{@docRoot}/doc-files/redis-example.png" alt="Redis example"></div>
 * <p>
 * <a href="RedisExecuter.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class RedisExecuter extends AbstractRedisExecuter {
    @SuppressWarnings("PMD.VariableNamingConventions")
    private final static JedisPool jedisPool = RedisPoolFactory.getInstance().getJedisPool();
    
    private final static class RedisExecuterBuilder {
        private final static RedisExecuter INSTANCE = new RedisExecuter();
    }
    
    public static RedisExecuter getInstance() {
        return RedisExecuterBuilder.INSTANCE;
    }

    /* (non-Javadoc)
     * @see com.zxy.commons.redis.execute.AbstractRedisExecuter#open()
     */
    @Override
    protected Jedis open() {
        return jedisPool.getResource();
    }

    /* (non-Javadoc)
     * @see com.zxy.commons.redis.execute.AbstractRedisExecuter#close(redis.clients.jedis.Jedis)
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void close(Jedis jedis) {
        if(jedis!=null && jedis.isConnected()){
            jedisPool.returnResource(jedis);
        }
    }

}
