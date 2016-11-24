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
package com.zxy.commons.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zxy.commons.lang.utils.KryoSeriizationUtils;

/**
 * KryoTest
 * 
 * <p>
 * <a href="KryoTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("PMD")
public class KryoTest {

    private long time;

    /**
     * beforeTest
    */
    @Before
    public void beforeTest() {
        time = System.currentTimeMillis();
    }

    /**
     * afterTest
    */
    @After
    public void afterTest() {
        System.out.println(System.currentTimeMillis() - time);
    }

    @Test
    public void testObject() throws IOException {
        Custom val = new Custom();
        val.setName("aaa");
        val.setValue("中华人民共和国");
        
//        byte[] bytes = KryoSerializers.getBytes(val);
//        Custom newValue = KryoSerializers.readObject(bytes, Custom.class);

        byte[] bytes = KryoSeriizationUtils.serializationObject(val);
        Custom newValue = KryoSeriizationUtils.deserializationObject(bytes, Custom.class);
        Assert.assertEquals(val.getName(), newValue.getName());
    }

    @Test
    public void testList() throws IOException {
        List<Custom> lst = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            Custom val = new Custom();
            val.setName("aaa"+i);
            val.setValue("中华人民共和国");
            lst.add(val);
        }

        byte[] bytes = KryoSeriizationUtils.serializationList(lst);
        List<Custom> newValue = KryoSeriizationUtils.deserializationList(bytes);
        newValue.forEach(c-> {
            System.out.println("name:"+c.getName());
        });
        Assert.assertEquals(lst.size(), newValue.size());
    }

    @Test
    public void testSet() throws IOException {
        Set<Custom> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            Custom val = new Custom();
            val.setName("aaa"+i);
            val.setValue("中华人民共和国");
            set.add(val);
        }
 
        byte[] bytes = KryoSeriizationUtils.serializationSet(set);
        Set<Custom> newValue = KryoSeriizationUtils.deserializationSet(bytes);
        newValue.forEach(c-> {
            System.out.println("name:"+c.getName());
        });
        Assert.assertEquals(set.size(), newValue.size());
    }
    
    @Test
    @SuppressWarnings("PMD.AddEmptyString")
    public void testMap() throws IOException {
        Map<String, Custom> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            Custom val = new Custom();
            val.setName("aaa"+i);
            val.setValue("中华人民共和国");
            map.put(""+i, val);
        }

        byte[] bytes = KryoSeriizationUtils.serializationMap(map);
        Map<String, Custom> newValue = KryoSeriizationUtils.deserializationMap(bytes);
        newValue.forEach((k, v) -> {
            System.out.println("k:"+k);
            System.out.println("v:"+v.getName());
        });
        Assert.assertEquals(map.size(), newValue.size());
    }
    
    @Test
    public void stream1Serialization() throws IOException {
        OutputStream output = new FileOutputStream(new File("/tmp/xxx.out"));
        Custom val = new Custom();
        val.setName("aaa");
        val.setValue("中华人民共和国");
        KryoSeriizationUtils.serializationObject(val, output);
        output.close();
    }
    
    @Test
    public void stream2Deserialization() throws IOException {
        stream1Serialization();
        InputStream input = new FileInputStream(new File("/tmp/xxx.out"));
        Custom val = KryoSeriizationUtils.deserializationObject(input, Custom.class);
        System.out.println("val="+val);
        Assert.assertEquals("aaa", val.getName());
        input.close();
    }
}
