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
package com.zxy.commons.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zxy.commons.json.JsonUtils;

/**
 * Redis test case
 * 
 * <p>
 * <a href="RedisUtilsTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:cache-redis.xml" })
@SuppressWarnings("PMD")
@Deprecated
public class RedisUtilsTest {
    private static final String KEY = "abc";

    /**
     * Set up
     * 
     * @throws Exception
     */
    @BeforeClass
    // @Before
    public static void setUp() {
        // do nothing
    }

    /**
     * Tear down
     */
    @AfterClass
    // @After
    public static void tearDown() {
        // do nothing
    }

    @Test
    public void redisStringTest() {
        String value = "123";
        RedisUtils.set(KEY.getBytes(), value.getBytes());
        byte[] vs = RedisUtils.get(KEY.getBytes());
        Assert.assertEquals(value, new String(vs));
    }

    @Test
    public void redisObjectTest() {
        JsonObject obj = new JsonObject();
        obj.setName("name");
        obj.setUser("user");
        RedisUtils.set4Json(KEY.getBytes(), obj);
        JsonObject obj2 = RedisUtils.getObject4Json(KEY.getBytes(), JsonObject.class);
        System.out.println("obj2=" + obj2);
        Assert.assertEquals(obj.getName(), obj2.getName());
    }

    @Test
    public void redisListTest() {
        JsonObject obj = new JsonObject();
        obj.setName("name");
        obj.setUser("user");

        JsonObject obj1 = new JsonObject();
        obj1.setName("name1");
        obj1.setUser("user1");

        RedisUtils.set4Json(KEY.getBytes(), Lists.newArrayList(obj, obj1));

        List<JsonObject> obj2 = RedisUtils.getList4Json(KEY.getBytes(), JsonObject.class);
        System.out.println("obj2=" + obj2);
        Assert.assertEquals(obj.getName(), obj2.get(0).getName());
    }

    @Test
    public void redisSetTest() {
        JsonObject obj = new JsonObject();
        obj.setName("name");
        obj.setUser("user");

        JsonObject obj1 = new JsonObject();
        obj1.setName("name1");
        obj1.setUser("user1");

        RedisUtils.set4Json(KEY.getBytes(), Sets.newHashSet(obj, obj1));

        Set<JsonObject> obj2 = RedisUtils.getSet4Json(KEY.getBytes(), JsonObject.class);
        System.out.println("obj2=" + obj2);
        Assert.assertEquals(true, obj2.size() > 0);
    }

    @Test
    public void redisMapTest() {
        JsonObject obj = new JsonObject();
        obj.setName("name");
        obj.setUser("user");
        Map<String, JsonObject> map = Maps.newHashMap();
        map.put("mapKey", obj);

        RedisUtils.set4Json(KEY.getBytes(), map);

        Map<String, JsonObject> map2 = RedisUtils.getMap4Json(KEY.getBytes(), String.class, JsonObject.class);
        System.out.println("map2=" + map2);
        Assert.assertEquals(true, map2.size() > 0);
    }

    @Test
    public void redisCumsterObjectTransferTest() throws JsonProcessingException {
        JsonObject obj = new JsonObject();
        obj.setName("name");
        obj.setUser("user");
        RedisUtils.set(KEY.getBytes(), JsonUtils.toJson(obj).getBytes());

        // JsonObject obj2 = RedisUtils.get(key.getBytes(), new
        // RedisTransferCallback<JsonObject>() {
        //
        // @Override
        // public JsonObject transfer(byte[] message) {
        // JsonObject obj = null;
        // try {
        // obj = JsonUtils.toObject(new String(message), JsonObject.class);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // return obj;
        // }
        // });

        JsonObject obj2 = RedisUtils.get(KEY.getBytes(), message -> {
            return JsonUtils.toObject(new String(message), JsonObject.class);
        });

        System.out.println("obj2=" + obj2);
        Assert.assertEquals(obj.getName(), obj2.getName());
    }
}
