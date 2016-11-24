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

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxy.commons.json.JsonUtils;
import com.zxy.commons.lang.idgenerator.IdUtils;
import com.zxy.commons.lang.utils.DatesUtils;
import com.zxy.commons.mq.consumer.ConsumerCallback;
import com.zxy.commons.mq.consumer.StringConsumer;
import com.zxy.commons.mq.producer.StringProducer;
import com.zxy.commons.mq.utils.KafkaUtils;

/** 
 * http://www.programcreek.com/java-api-examples/index.php?api=kafka.utils.ZkUtils
 * 
 * <p>
 * <a href="ZookeeperUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
@SuppressWarnings({"PMD"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KafkaTest {
    private static final String GROUP_ID = "KafkaUtils-test-topic-group";
    private static final String TOPIC = "KafkaUtils-test-topic";
    private static final Long SEEK_OFFSET = 0L;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Set up
     * 
     * @throws Exception
     */
    @BeforeClass
    public static void setUp() {
        createTopic();
    }

    /**
     * Tear down
     */
    @AfterClass
    public static void tearDown() {
        deleteTopic();
    }
    
    private static void createTopic() {
//        if(!KafkaUtils.topicExists(TOPIC)) {
        int partitions = 3;
        int replication = 1;
        KafkaUtils.createTopic(TOPIC, partitions, replication);
//        }
    }
    
    private static void deleteTopic() {
        KafkaUtils.deleteTopic(TOPIC);
    }
    
    @Test
    public void producer() throws InterruptedException {
        int num = KafkaUtils.getPartitionNums(TOPIC);
        for(int i=0; i < num; i++) {
            long id = IdUtils.genLongId();
            User user = new User();
            user.setId(id);
            user.setName(DatesUtils.YYMMDDHHMMSS.toString());
            user.setPwd(String.valueOf(id));
//            UserProducer.producer("test", String.valueOf(id), user);
            StringProducer.producer(TOPIC, String.valueOf(id), JsonUtils.toJson(user));
        }
        Map<Integer,Long> logsizes = KafkaUtils.getLogsizes(TOPIC);
//        System.out.println(logsizes);
        long logsizeCount = 0L;
        for(Map.Entry<Integer, Long> value : logsizes.entrySet()) {
            logsizeCount += value.getValue();
        }
        Assert.assertTrue(logsizeCount == num);
    }
    
    @Test
    public void consumer() throws InterruptedException {
        StringConsumer.createConsumer(GROUP_ID, TOPIC).subscribe(new ConsumerCallback<String>() {

            @Override
            public void process(String topic, long offset, int partition, String message) {
                logger.info("message=========="+message);
            }

            @Override
            public void exceptions(String topic, long offset, int partition, String message, Throwable ex) {
                logger.error(ex.getMessage(), ex);
            }
        });
    }
    
    @Test 
    public void getPartitionNums() {
        Assert.assertEquals(true, KafkaUtils.getPartitionNums(TOPIC) > 0);
    }
    
    @Test 
    public void seekPartition() {
        int num = KafkaUtils.getPartitionNums(TOPIC);
        for(int i = 0;i < num; i++) {
            KafkaUtils.seekPartition(TOPIC, GROUP_ID, i, SEEK_OFFSET);
        }
        Assert.assertEquals(true, num > 0);
    }
    
    @Test 
    public void seekAllPartition() {
        KafkaUtils.seekAllPartition(TOPIC, GROUP_ID, SEEK_OFFSET);
    }
    
    @Test
    public void getOffset() {
        int num = KafkaUtils.getPartitionNums(TOPIC);
        for(int i = 0;i < num; i++) {
            long offset = KafkaUtils.getOffset(TOPIC, GROUP_ID, i);
//            System.out.println("offset: "+offset+", SEEK_OFFSET: "+SEEK_OFFSET);
            Assert.assertTrue(offset >= 0);
        }
    }
    
    @Test
    public void getOffsets() {
        Map<Integer,Long> map = KafkaUtils.getOffsets(TOPIC, GROUP_ID);
        Assert.assertEquals(false, map.isEmpty());
    }
    
    @Test
    public void getLogsize() {
        int num = KafkaUtils.getPartitionNums(TOPIC);
        for(int i = 0;i < num; i++) {
            long logsize = KafkaUtils.getLogsize(TOPIC, i);
            Assert.assertTrue(logsize >= 0);
        }
    }
    
    @Test
    public void getLogsizes() {
        Map<Integer,Long> map = KafkaUtils.getLogsizes(TOPIC);
        Assert.assertEquals(false, map.isEmpty());
    }
    
    @Test
    public void getLag() {
        int num = KafkaUtils.getPartitionNums(TOPIC);
        for(int i = 0;i < num; i++) {
            long lag = KafkaUtils.getLag(TOPIC, GROUP_ID, i);
            Assert.assertTrue(lag >= 0);
        }
    }
    
    @Test
    public void getLags() {
        Map<Integer,Long> map = KafkaUtils.getLags(TOPIC, GROUP_ID);
        Assert.assertEquals(false, map.isEmpty());
    }
}
