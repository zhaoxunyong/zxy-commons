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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.zxy.commons.lang.idgenerator.IdUtils;

/**
 * Id gen test.
 * 
 * <p>
 * <a href="IdTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class IdTest {
    private final static int SIZE = 2048;
    private ExecutorService service;

    /**
     * Set up
     * 
     * @throws Exception
     */
    @Before
    public void setUp() {
        service = Executors.newCachedThreadPool();
    }

    /**
     * Tear down
     */
    @After
    public void tearDown() {
        service.shutdown();
    }

    @Test
    public void longIdGenTest() throws InterruptedException {
        final List<Long> ids = Lists.newCopyOnWriteArrayList();
        CountDownLatch cd = new CountDownLatch(SIZE);
        for (int i = 1; i <= SIZE; i++) {
            service.execute(() -> {
                try {
                    long id = IdUtils.genLongId();
                    if (ids.contains(id)) {
                        System.err.println("====" + id);
                    } else {
                        ids.add(id);
                    }
                } finally {
                    cd.countDown();
                }
            });
        }
        cd.await();
        System.out.println("ids size:" + ids.size());
        Assert.assertEquals(SIZE, ids.size());
    }

    @Test
    public void stringIdGenTest() throws InterruptedException {
        final List<String> ids = Lists.newCopyOnWriteArrayList();
        CountDownLatch cd = new CountDownLatch(SIZE);
        for (int i = 1; i <= SIZE; i++) {
            service.execute(() -> {
                try {
                    String id = IdUtils.genStringId();
                    if (ids.contains(id)) {
                        System.err.println("====" + id);
                    } else {
                        ids.add(id);
                    }
                } finally {
                    cd.countDown();
                }
            });
        }
        cd.await();
        System.out.println("ids size:" + ids.size());
        Assert.assertEquals(SIZE, ids.size());
    }
}
