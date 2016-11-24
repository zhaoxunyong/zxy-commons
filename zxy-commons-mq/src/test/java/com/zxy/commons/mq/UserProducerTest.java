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
package com.zxy.commons.mq;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zxy.commons.lang.idgenerator.IdUtils;
import com.zxy.commons.mq.producer.ObjectProducer;

/**
 * Producer test
 * 
 * <p>
 * <a href="ProducerTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("PMD")
public class UserProducerTest {
    private static final String TOPIC = "KafkaUtils-test-topic";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * producerTest
     * @throws InterruptedException 
     */
    public void producerTest() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        int count = 10;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            final int xxx = i;
            service.execute(() -> {
                long id = IdUtils.genLongId();
//                AbstractProducer.send("test", String.valueOf(id), "This is " + i + " test");
                User user = new User();
                user.setId(id);
                user.setName("zhaoxunyong"+xxx);
                user.setPwd("000000");
//                UserProducer.producer("test", String.valueOf(id), user);
                try {
                    ObjectProducer.producer4Json(TOPIC, String.valueOf(id), user);
                } finally {
                    latch.countDown();
                }
            });
//            TimeUnit.MILLISECONDS.sleep(5L);
        }
        latch.await();
        service.shutdown();
        service.awaitTermination(3, TimeUnit.SECONDS);
    }
    
    /**
     * producerTest
     * @throws InterruptedException 
     */
    public void producerListTest() throws InterruptedException {
        int count = 10;
        List<User> users = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            long id = IdUtils.genLongId();
            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            User user = new User();
            user.setId(id);
            user.setName("zhaoxunyong"+i);
            user.setPwd("000000");
            users.add(user);
        }

        ObjectProducer.producerList4Json(TOPIC, IdUtils.genStringId(), users);
    }

    /**
     * producerTest
     * @throws InterruptedException 
     */
    public void producerMapTest() throws InterruptedException {
        int count = 10;
        Map<Long, User> users = Maps.newHashMap();
        for (int i = 0; i < count; i++) {
            long id = IdUtils.genLongId();
            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            User user = new User();
            user.setId(id);
            user.setName("zhaoxunyong"+i);
            user.setPwd("000000");
            users.put(id, user);
        }

        ObjectProducer.producerMap4Json(TOPIC, IdUtils.genStringId(), users);
    }
    
    /**
     * test
     */
    public void producerTest2() {
        int count = 2;
        for (int i = 0; i < count; i++) {
            long id = IdUtils.genLongId();
            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            User user = new User();
            user.setName("zhaoxunyong"+i);
            user.setPwd("000000");
            ObjectProducer.producer4Json("test100", String.valueOf(id), user);
        }
    }

    /**
     * Main
     * 
     * @param args args
     * @throws Exception Exception
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public static void main(String[] args) throws Exception {
        new UserProducerTest().producerMapTest();
    }
}
