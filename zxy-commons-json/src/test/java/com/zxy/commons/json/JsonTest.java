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
package com.zxy.commons.json;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/** 
 * Json test case
 * 
 * <p>
 * <a href="JsonTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class JsonTest {
    private Jsons jsons;
    /**
     * setUp
     */
    @Before
    public void setUp() {
        jsons = new Jsons();
        jsons.setInt1(2);
        jsons.setLong1(1L);
        jsons.setStr1("aaa");
        jsons.setStr2("bbb");
    }

    /**
     * tearDown
     */
    @After
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void tearDown() {
    }
    
    @Test
    public void object() {
        String jsonStr = JsonUtils.toJson(jsons);
        System.out.println("json str:"+jsonStr);
        
        String json = JsonUtils.toJson(Include.NON_EMPTY, jsons);
        System.out.println("json:"+json);
        
        Assert.assertNotNull(json);
    }
    
    @Test
    public void list() {
        List<Jsons> objects = Lists.newArrayList(jsons);
        String listStr = JsonUtils.toJson(objects);
        System.out.println("listStr:"+listStr);
        
        List<Jsons> list = JsonUtils.toList(listStr, Jsons.class);
        list.forEach(obj -> System.out.println(obj.getStr1()));
        Assert.assertNotNull(list);
    }
    
    @Test
    public void set() {
        Set<Jsons> objects = Sets.newHashSet(jsons);
        String setStr = JsonUtils.toJson(objects);
        System.out.println("setStr:"+setStr);
        
        Set<Jsons> set = JsonUtils.toSet(setStr, Jsons.class);
        set.forEach(obj -> System.out.println(obj.getStr1()));
        Assert.assertNotNull(set);
    }
    
    @Test
    public void map() {
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("0", 11L);
        map.put("1-10", 22L);
        map.put("11-30", 20L);
        map.put("31-100", 25L);
        map.put("100以上", 15L);
        
        String mapString = JsonUtils.toJson(map);
        System.out.println("mapString:"+mapString);
        
        Map<String, Long> maps = JsonUtils.toMap(mapString, String.class, Long.class);
//        for(Map.Entry<String, Long> value : maps.entrySet()) {
//            System.out.println("key: "+value.getKey()+", value: "+value.getValue());
//        }
        maps.forEach((key, value) -> {
            System.out.println("key: "+key+", value: "+value);
        });
        Assert.assertNotNull(maps);
    }
    
    @Test
    public void jsonsTest() {
        JsonObject object = JsonObject.create();
        object.put("a1", "bbbbbb");
        object.put("a2", 111222L);
        object.put("a3", true);
        System.out.println("object1:"+object);
        System.out.println("object2:"+JsonObject.create(object.toJSONString()).getBoolean("a3"));
        Assert.assertNotNull(object);

        String json = JsonObject.create().put("a1", "bbbbbb")
                .put("a2", 111222L)
                .put("a3", true)
                .toJSONString();
        System.out.println("json==="+json);
        Assert.assertNotNull(json);
    }
}
