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


import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxy.commons.json.JsonUtils;
import com.zxy.commons.lang.idgenerator.IdUtils;
import com.zxy.commons.lang.utils.DatesUtils;
import com.zxy.commons.mq.producer.StringProducer;
import com.zxy.commons.mq.utils.KafkaUtils;

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
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public class StringProducerTest {
    private static final String TOPIC = "KafkaUtils-test-topic";
    protected final static Logger LOGGER = LoggerFactory.getLogger(StringProducerTest.class);

    /**
     * producerTest
     * @throws InterruptedException 
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void producerTest() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1);
        int count = 10;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            service.execute(() -> {
                long id = IdUtils.genLongId();
                User user = new User();
                user.setId(id);
                user.setName(DatesUtils.YYMMDDHHMMSS.toString());
                user.setPwd(String.valueOf(id));
//                UserProducer.producer("test", String.valueOf(id), user);
                try {
                    StringProducer.producer(TOPIC, String.valueOf(id), JsonUtils.toJson(user));
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
     * Main
     * 
     * @param args args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        new StringProducerTest().producerTest();
        Map<Integer,Long> logsizes = KafkaUtils.getLogsizes(TOPIC);
        System.out.println(logsizes);
    }
}
