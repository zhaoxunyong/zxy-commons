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
package com.zxy.commons.mq.producer;

import java.util.Properties;

import com.zxy.commons.mq.prop.KafkaProducerConfigProperties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * AbstractProducer
 * 
 * <p>
 * <a href="AbstractProducer.java"><i>View Source</i></a>
 * 
 * @param <K> key
 * @param <V> value
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings({"PMD"})
public abstract class AbstractProducer<K, V> {
    private final static KafkaProducerConfigProperties PROP = KafkaProducerConfigProperties.getInstance();
    private final ProducerConfig producerConfig;
    private final Producer<K, V> producerStr;
    
    /**
     * 指定内容的序列化对象
     * @return Class
     */
    protected abstract Class<?> getValueSerializerClass();
    
    /**
     * 指定路由key的序列化对象
     * @return Class
     */
    protected abstract Class<?> getKeySerializerClass();
    
//    /**
//     * 指定路由规则的序列化对象
//     */
//    protected abstract Class<?> getRouterPartitionerClass();

    public AbstractProducer(ProducerType producerType) {
     // 生产者参数配置
        Properties properties = new Properties();
        properties.setProperty("metadata.broker.list", PROP.getMetadataBrokerList());
        // 消息序列化类,将消息实体转换成byte[]
        Class<?> valueSerializerClass = getValueSerializerClass();
        Class<?> keySerializerClass = getKeySerializerClass();
        if(valueSerializerClass != null) {
            properties.setProperty("serializer.class", valueSerializerClass.getName());
        }
        if(keySerializerClass != null) {
            properties.setProperty("key.serializer.class", keySerializerClass.getName());
        }
        Class<?> routerPartitionerClass = getKeySerializerClass();
        if(routerPartitionerClass != null) {
            // partitions路由类,消息在发送时将根据此实例的方法获得partition索引号.
            properties.setProperty("partitioner.class", routerPartitionerClass.getName());
        }
        /*
         * 0: producer不会等待broker发送ack 
         * 1: 当leader接收到消息之后发送ack 
         * 2: 当所有的follower都同步消息成功后发送ack.
         */
        properties.setProperty("request.required.acks", PROP.getRequestRequiredAcks());
        
        String type = PROP.getProducerType();
        if(producerType != null) {
            type = producerType.toString();
        }
        // producer消息发送的模式,同步或异步.
        properties.setProperty("producer.type", type);
        
        // 消息在producer端buffer的条数
        properties.setProperty("batch.num.messages", PROP.getBatchNumMessages());
        /*
         * 在async模式下,当message被缓存的时间超过此值后,将会批量发送给broker 此值和batch.num.messages协同工作
         */
        properties.setProperty("queue.buffering.max.ms", PROP.getQueueBufferingMaxMs());

        producerConfig = new ProducerConfig(properties);
        producerStr = new Producer<>(producerConfig);
    }

    /**
     * 
     * 提交消息
     * 
     * @param topic topic 
     * @param routerKey 路由key，为空时会随机写入 
     * @param value value
     */
    public void sendMessage(String topic, K routerKey, V value) {
        if (value != null) {
            KeyedMessage<K, V> km = new KeyedMessage<K, V>(topic, routerKey, value);
            producerStr.send(km);
        }
    }
}
