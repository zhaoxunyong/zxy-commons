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
package com.zxy.commons.modelmapper;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Automapper test.
 * 
 * <p>
 * <a href="MapperTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:commons-modelmapper.xml" })
public class MapperTest {
    @Autowired
    private ModelMapper modelMapper;
    
    /**
     * Set up
     * 
     * @throws Exception
     */
    @BeforeClass
    // @Before
    @SuppressWarnings("PMD.JUnit4TestShouldUseBeforeAnnotation")
    public static void setUp() {
        // do nothing
    }

    /**
     * Tear down
     */
    @AfterClass
    // @After
    @SuppressWarnings("PMD.JUnit4TestShouldUseAfterAnnotation")
    public static void tearDown() {
        // do nothing
    }

    @Test
    public void testMap() {
        Person person = new Person();
        Address address = new Address();
        address.setCity("city");
        address.setStreet("street");
        person.setAddress(address);
        PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);
        System.out.println("personDTO city:"+personDTO.getCity());
        System.out.println("personDTO street:"+personDTO.getStreet());
        Assert.assertEquals("city", personDTO.getCity());
    }
}
