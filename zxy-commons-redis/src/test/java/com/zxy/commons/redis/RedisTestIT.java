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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.zxy.commons.redis.Executer.RedisExecution;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * Redis test case
 * 
 * <p>
 * <a href="MemcacheTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations="classpath:applicationContext.xml")
public class RedisTestIT {

    /**
     * setUp
     */
    @Before
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void setUp() {
    }

    /**
     * tearDown
     */
    @After
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void tearDown() {
    }

    /**
     * Redis cluster test
    */
//    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void redisClusterTest() {
        JedisCluster client = RedisClusterFactory.getInstance().getJedisCluster();
        client.set("xxx", "222");
        Assert.assertEquals("222", client.get("xxx"));
    }

//    @Test
    /**
     * redis test
    */
    public void redisTest() {
        RedisExecuter.getInstance().execute(new RedisExecution<String>() {

            @Override
            public String execute(Jedis jedis) {
                jedis.set("xxx", "222");
                return jedis.get("xxx");
            }
        });
        String value2 = RedisExecuter.getInstance().execute(jedis -> jedis.get("xxx"));
        Assert.assertEquals("222", value2);
    }
}
