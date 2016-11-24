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
package com.zxy.commons.mq.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxy.commons.lang.exception.DatasAccessException;
import com.zxy.commons.mq.prop.KafkaConsumerConfigProperties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

/**
 * 消费者基类
 * 
 * <p>
 * <a href="AbstractConsumer.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractConsumer {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static KafkaConsumerConfigProperties PROP = KafkaConsumerConfigProperties.getInstance();
    private final ConsumerConnector consumer;
    private final ExecutorService executor;
    private final String topic;
    private final Integer threadNum;
    @SuppressWarnings({ "PMD.AvoidUsingVolatile", "PMD.RedundantFieldInitializer" })
    private volatile boolean isSubscribed = false;

    /**
     * 构造函数
     * 
     * <p>
     * threadNum默认为cpu的核数
     * 
     * @param groupId groupId
     * @param topic topic
     */
    public AbstractConsumer(String groupId, String topic) {
        this(groupId, topic, 1);
    }

    /**
     * 构造函数
     * 
     * @param groupId groupId
     * @param topic topic
     * @param threadNum thread number
     */
    public AbstractConsumer(String groupId, String topic, Integer threadNum) {
        this.topic = topic;
        if(threadNum != null) {
            this.threadNum = threadNum;
            this.executor = Executors.newFixedThreadPool(threadNum);
        } else {
            this.threadNum = 1;
            this.executor = Executors.newSingleThreadExecutor();
        }
        Properties props = new Properties();
        props.put("zookeeper.connect", PROP.getZookeeperConnect());
        props.put("group.id", groupId);
        props.put("zookeeper.session.timeout.ms", PROP.getZookeeperSessionTimeout());
        props.put("zookeeper.sync.time.ms", PROP.getZookeeperSyncTime());
        props.put("auto.commit.interval.ms", PROP.getAutoCommitInterval());
        String autoCommitEnable = PROP.getAutoCommitEnable();
        if(StringUtils.isNotBlank(autoCommitEnable)) {
            props.put("auto.commit.enable", autoCommitEnable);
        }
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
    }

    /**
     * 关闭消息线程
     */
    public void shutdown() {
        if (consumer != null) {
            consumer.shutdown();
        }
        isSubscribed = false;
        if (executor != null) {
            try {
                executor.shutdown();
                executor.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if(!autoCommitDisable()) {
            try {
                // 防止shutdown时offset没有同步到zookeeper
                TimeUnit.MILLISECONDS.sleep(1000L);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }
    
    private boolean autoCommitDisable() {
        String autoCommitEnable = PROP.getAutoCommitEnable();
        return "false".equalsIgnoreCase(autoCommitEnable);
    }
    
    private void commit() {
        if(autoCommitDisable()) {
            try {
                consumer.commitOffsets();
            } catch(Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * decode
     * 
     * @param messages messages
     * @return T
    */
//    protected abstract V decode(byte[] messages);

    /**
     * subscribe
     * 
     * @param consumerCallback ConsumerCallback
    */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    protected void subscribeProcess(ConsumerCallback<byte[]> consumerCallback) {
        if(isSubscribed) {
            throw new DatasAccessException(topic +" consumer is already subscribed!");
        }
        isSubscribed = true;
        // now create an object to consume the messages
        
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, threadNum);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        for (final KafkaStream<byte[], byte[]> stream : streams) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    ConsumerIterator<byte[], byte[]> it = stream.iterator();
                    consumber(it, consumerCallback);
                }
            });
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                shutdown();
            }
        });
    }
    
    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    private void consumber(ConsumerIterator<byte[], byte[]> it, ConsumerCallback<byte[]> consumerCallback) {
        while (it.hasNext()) {
            MessageAndMetadata<byte[], byte[]> messageAndMetadata = it.next();
            byte[] message = messageAndMetadata.message();
            long offset = messageAndMetadata.offset();
            int partition = messageAndMetadata.partition();
            try {
//                codedMessage = decode(srcMessage);
//                message = new String(messageAndMetadata.message(), "utf-8");
//                logger.debug("topic: {}, message: {}", topic, message);
                if(consumerCallback!=null) {
                    consumerCallback.process(topic, offset, partition, message);
                    commit();
                }
            } catch (Throwable ex) {
                try {
//                        ConsumerException<V> consumerException = new ConsumerException<V>(message, ex);
                    consumerCallback.exceptions(topic, offset, partition, message, ex);
                }catch(Throwable e1){
                    logger.error("处理失败消费异常, topic: {}", topic, e1);
                }
            }
        }
    }
//    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private final static KafkaConsumerConfigProperties PROP = KafkaConsumerConfigProperties.getInstance();
//    @SuppressWarnings("PMD.AvoidUsingVolatile")
//    private final ConsumerConnector consumer;
//    private final ExecutorService executor;
//
//    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
//    public AbstractConsumer() {
//        executor = Executors.newFixedThreadPool(getThreadNum());
//        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig());
//    }
//
//    /**
//     * Get groupId
//     * 
//     * @return groupId
//     */
//    public abstract String getGroupId();
//
//    /**
//     * Get topic
//     * 
//     * @return topic
//     */
//    public abstract String getTopic();
//
//    /**
//     * Get thread number
//     * 
//     * @return thread number
//     */
//    public abstract int getThreadNum();
//
//    /**
//     * 处理业务逻辑
//     * 
//     * @param message message
//     */
//    public abstract void process(String message);
//
//    /**
//     * Create consumer config
//     * 
//     * @return ConsumerConfig
//     */
//    public ConsumerConfig createConsumerConfig() {
//        Properties props = new Properties();
//        props.put("zookeeper.connect", PROP.getZookeeperConnect());
//        props.put("group.id", getGroupId());
//        props.put("zookeeper.session.timeout.ms", PROP.getZookeeperSessionTimeout());
//        props.put("zookeeper.sync.time.ms", PROP.getZookeeperSyncTime());
//        props.put("auto.commit.interval.ms", PROP.getAutoCommitInterval());
//        return new ConsumerConfig(props);
//    }
//
//    /**
//     * 启动消息线程
//     */
//    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
//    public void run() {
//        String topic = getTopic();
//        int threadNum = getThreadNum();
//        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
//        topicCountMap.put(topic, threadNum);
//        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
//        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
//        // now create an object to consume the messages
//        int threadNumber = 1;
//        for (final KafkaStream<byte[], byte[]> stream : streams) {
//            final int num = threadNumber;
//            executor.execute(new Runnable() {
//                @Override
//                @SuppressWarnings("PMD.AvoidCatchingThrowable")
//                public void run() {
//                    ConsumerIterator<byte[], byte[]> it = stream.iterator();
//                    while (it.hasNext()) {
//                        String message = null;
//                        try {
//                            MessageAndMetadata<byte[], byte[]> messageAndMetadata = it.next();
//                            message = new String(messageAndMetadata.message(), "utf-8");
//                            logger.debug("topic: {}, message: {}", topic, message);
//                            process(message);
//                        } catch (Throwable e) {
//                            logger.error("消费失败。threadNumber={}, topic: {}, message={}", num, topic, message, e);
//                        }
//                    }
//                }
//            });
//            threadNumber++;
//        }
//
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                shutdown();
//                try {
//                    executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
//                } catch (InterruptedException e) {
//                    logger.error(e.getMessage(), e);
//                }
//            }
//        });
//    }
//
//    /**
//     * 关闭消息线程
//     */
//    public void shutdown() {
//        if (consumer != null) {
//            consumer.shutdown();
//        }
//        if (executor != null) {
//            executor.shutdown();
//        }
//    }
}
