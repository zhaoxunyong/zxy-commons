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

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.SerializationUtils;

import com.google.common.base.Charsets;
import com.zxy.commons.json.JsonUtils;
import com.zxy.commons.lang.exception.DatasAccessException;
import com.zxy.commons.lang.utils.KryoSeriizationUtils;
import com.zxy.commons.mq.serialize.StringSerialize;

/** 
 * Byte producer
 * 
 * <p>
 * <a href="ObjectProducer.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class ObjectProducer extends AbstractProducer<String, byte[]> {
    private final static class ObjectProducerBuilder {
        private final static ObjectProducer BUILDER = new ObjectProducer(null);
    }
//    private final static class AsyncObjectProducerBuilder {
//        private final static ObjectProducer BUILDER = new ObjectProducer(ProducerType.async);
//    }
//    private final static class SyncObjectProducerBuilder {
//        private final static ObjectProducer BUILDER = new ObjectProducer(ProducerType.sync);
//    }
    
    private ObjectProducer(ProducerType producerType){
        super(producerType);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getValueSerializerClass() {
        // 返回null，表示不需要编码
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getKeySerializerClass() {
        return StringSerialize.class;
    }
    
    /**
     * Producer message
     * 
     * @param topic topic 
     * @param routerKey 路由key，为空时会随机写入 
     * @param value value
    */
    public static void producer(String topic, String routerKey, byte[] value) {
        ObjectProducerBuilder.BUILDER.sendMessage(topic, routerKey, value);
    }
    
    /**
     * Producer message
     * 
     * <p>
     * 通过kafka_producer.properties的producer.type决定是同步还是异步
     * 
     * @param <T> value class
     * @param topic topic 
     * @param routerKey 路由key，为空时会随机写入 
     * @param value value
    */
    public static <T> void producer4Kryo(String topic, String routerKey, T value) {
        try {
            byte[] bytes = KryoSeriizationUtils.serializationObject(value);
            ObjectProducerBuilder.BUILDER.sendMessage(topic, routerKey, bytes);
        } catch (IOException e) {
            throw new DatasAccessException(e.getMessage(), e);
        }
    }
    
    /**
     * Producer message
     * 
     * <p>
     * 通过kafka_producer.properties的producer.type决定是同步还是异步
     * 
     * @param <T> value class
     * @param topic topic 
     * @param routerKey 路由key，为空时会随机写入 
     * @param value value
    */
    public static <T> void producer4Jdk(String topic, String routerKey, Serializable value) {
        byte[] bytes = SerializationUtils.serialize(value);
        ObjectProducerBuilder.BUILDER.sendMessage(topic, routerKey, bytes);
    }
    
    /**
     * Producer message
     * 
     * <p>
     * 通过kafka_producer.properties的producer.type决定是同步还是异步
     * 
     * @param <T> value class
     * @param topic topic 
     * @param routerKey 路由key，为空时会随机写入 
     * @param value value
    */
    public static <T> void producer4Json(String topic, String routerKey, T value) {
        byte[] bytes = JsonUtils.toJson(value).getBytes(Charsets.UTF_8);
        ObjectProducerBuilder.BUILDER.sendMessage(topic, routerKey, bytes);
    }
    
    /**
     * Producer message
     * 
     * <p>
     * 通过kafka_producer.properties的producer.type决定是同步还是异步
     * 
     * @param <T> value class
     * @param topic topic 
     * @param routerKey 路由key，为空时会随机写入 
     * @param value value
    */
    public static <T> void producerList4Json(String topic, String routerKey, List<T> value) {
        byte[] bytes = JsonUtils.toJson(value).getBytes(Charsets.UTF_8);
        ObjectProducerBuilder.BUILDER.sendMessage(topic, routerKey, bytes);
    }
    
    /**
     * Producer message
     * 
     * <p>
     * 通过kafka_producer.properties的producer.type决定是同步还是异步
     * 
     * @param <T> value class
     * @param topic topic 
     * @param routerKey 路由key，为空时会随机写入 
     * @param value value
    */
    public static <T> void producerSet4Json(String topic, String routerKey, Set<T> value) {
        byte[] bytes = JsonUtils.toJson(value).getBytes(Charsets.UTF_8);
        ObjectProducerBuilder.BUILDER.sendMessage(topic, routerKey, bytes);
    }
    
    /**
     * Producer message
     * 
     * <p>
     * 通过kafka_producer.properties的producer.type决定是同步还是异步
     * 
     * @param <K> key
     * @param <V> value
     * @param topic topic 
     * @param routerKey 路由key，为空时会随机写入 
     * @param value value
    */
    public static <K, V> void producerMap4Json(String topic, String routerKey, Map<K, V> value) {
        byte[] bytes = JsonUtils.toJson(value).getBytes(Charsets.UTF_8);
        ObjectProducerBuilder.BUILDER.sendMessage(topic, routerKey, bytes);
    }
}
