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

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.zxy.commons.lang.utils.CollectionsUtils;

/** 
 * Object transform test
 * 
 * <p>
 * <a href="ObjectTransformTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class ObjectTransformTest {
    /**
     * Set up
     * 
     * @throws Exception
     */
    @Before
    public void setUp() {
        // do nothing
    }

    /**
     * Tear down
     */
    @After
    public void tearDown() {
        // do nothing
    }

    @Test
    public void models2dtos() {
        List<Object1> models = Lists.newArrayList(new Object1("a", "a1"), new Object1("b", "b1"));
//        List<Object2> dtos = CollectionsUtils.models2dtos(models, new ModelDtoMapping<Object1, Object2>() {
//
//            @Override
//            public Object2 transform(Object1 s1) {
//                return new Object2(s1.getUsername(), s1.getPassword());
//            }
//
//        });
        List<Object2> dtos = CollectionsUtils.models2dtos(models, model -> {
            return new Object2(model.getUsername(), model.getPassword());
        });
        System.out.println("dtos:"+dtos);
        Assert.assertNotNull(dtos);
        
    }

    @Test
    public void dtos2models() {
        List<Object2> dtos = Lists.newArrayList(new Object2("a", "a1"), new Object2("b", "b1"));
        List<Object1> models = CollectionsUtils.models2dtos(dtos, dto -> {
            return new Object1(dto.getName(), dto.getPwd());
        });
        System.out.println("models:"+models);
        Assert.assertNotNull(models);
        
    }
}
