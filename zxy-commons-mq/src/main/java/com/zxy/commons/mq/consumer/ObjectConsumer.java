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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.SerializationUtils;

import com.google.common.base.Charsets;
import com.zxy.commons.json.JsonUtils;
import com.zxy.commons.lang.utils.KryoSeriizationUtils;

/**
 * Object consumer
 * 
 * <p>
 * <a href="ObjectConsumer.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals"})
public final class ObjectConsumer extends AbstractConsumer {

    /**
     * @param groupId
     * @param topic
     */
    private ObjectConsumer(String groupId, String topic) {
        super(groupId, topic);
    }

    /**
     * @param groupId
     * @param topic
     * @param threadNum threadNum
     */
    private ObjectConsumer(String groupId, String topic, int threadNum) {
        super(groupId, topic, threadNum);
    }

    /**
     * CreateConsumer
     * 
     * @param groupId groupId
     * @param topic topic
     * @return BytesConsumer
     */
    public static ObjectConsumer createConsumer(String groupId, String topic) {
        return new ObjectConsumer(groupId, topic);
    }

    /**
     * CreateConsumer
     * 
     * @param groupId groupId
     * @param topic topic
     * @param threadNum threadNum
     * @return BytesConsumer
     */
    public static ObjectConsumer createConsumer(String groupId, String topic, int threadNum) {
        return new ObjectConsumer(groupId, topic, threadNum);
    }
    
    /**
     * subscribe4Kryo
     * 
     * @param <T> value type
     * @param clazz class
     * @param consumerCallback consumerCallback
     */
    public <T> void subscribe4Kryo(Class<T> clazz, ConsumerCallback<T> consumerCallback) {
        this.subscribeProcess(new ConsumerCallback<byte[]>() {
            
            @Override
            public void process(String topic, long offset, int partition, byte[] message) {
                T value = KryoSeriizationUtils.deserializationObject(message, clazz);
                logger.debug("topic, offset = {}, partition = {}, value = {}", topic, offset, partition, value);
                consumerCallback.process(topic, offset, partition, value);
            }
            
            @Override
            public void exceptions(String topic, long offset, int partition, byte[] message, Throwable ex) {
                T value = KryoSeriizationUtils.deserializationObject(message, clazz);
                logger.error("topic, offset = {}, partition = {}, value = {}", topic, offset, partition, value, ex);
                consumerCallback.exceptions(topic, offset, partition, value, ex);
            }
        });
    }
    
    /**
     * subscribe4Jdk
     * 
     * @param <T> value type
     * @param clazz class
     * @param consumerCallback consumerCallback
     */
    public <T> void subscribe4Jdk(Class<T> clazz, ConsumerCallback<T> consumerCallback) {
        this.subscribeProcess(new ConsumerCallback<byte[]>() {
            
            @Override
            public void process(String topic, long offset, int partition, byte[] message) {
                @SuppressWarnings("unchecked")
                T value = (T)SerializationUtils.deserialize(message);
                logger.debug("topic, offset = {}, partition = {}, value = {}", topic, offset, partition, value);
                consumerCallback.process(topic, offset, partition, value);
            }
            
            @Override
            public void exceptions(String topic, long offset, int partition, byte[] message, Throwable ex) {
                @SuppressWarnings("unchecked")
                T value = (T)SerializationUtils.deserialize(message);
                logger.error("topic, offset = {}, partition = {}, value = {}", topic, offset, partition, value, ex);
                consumerCallback.exceptions(topic, offset, partition, value, ex);
            }
        });
    }
    
    /**
     * subscribe4Json
     * 
     * @param <T> value type
     * @param clazz class
     * @param consumerCallback consumerCallback
     */
    public <T> void subscribe4Json(Class<T> clazz, ConsumerCallback<T> consumerCallback) {
        this.subscribeProcess(new ConsumerCallback<byte[]>() {
            
            @Override
            public void process(String topic, long offset, int partition, byte[] message) {
                String jsonString = new String(message, Charsets.UTF_8);
                logger.debug("topic, offset = {}, value = {}", topic, offset, jsonString);
                T value = JsonUtils.toObject(jsonString, clazz);
                consumerCallback.process(topic, offset, partition, value);
            }
            
            @Override
            public void exceptions(String topic, long offset, int partition, byte[] message, Throwable ex) {
                String jsonString = new String(message, Charsets.UTF_8);
                logger.error("topic, offset = {}, value = {}", topic, offset, jsonString, ex);
                T value = JsonUtils.toObject(jsonString, clazz);
                consumerCallback.exceptions(topic, offset, partition, value, ex);
            }
        });
    }
    
    /**
     * subscribeList4Json
     * 
     * @param <T> value type
     * @param clazz class
     * @param consumerCallback consumerCallback
     */
    public <T> void subscribeList4Json(Class<T> clazz, ConsumerCallback<List<T>> consumerCallback) {
        this.subscribeProcess(new ConsumerCallback<byte[]>() {
            
            @Override
            public void process(String topic, long offset, int partition, byte[] message) {
                String jsonString = new String(message, Charsets.UTF_8);
                logger.debug("topic, offset = {}, value = {}", topic, offset, jsonString);
                List<T> value = JsonUtils.toList(jsonString, clazz);
                consumerCallback.process(topic, offset, partition, value);
            }
            
            @Override
            public void exceptions(String topic, long offset, int partition, byte[] message, Throwable ex) {
                String jsonString = new String(message, Charsets.UTF_8);
                logger.error("topic, offset = {}, value = {}", topic, offset, jsonString, ex);
                List<T> value = JsonUtils.toList(jsonString, clazz);
                consumerCallback.exceptions(topic, offset, partition, value, ex);
            }
        });
    }
    
    /**
     * subscribeSet4Json
     * 
     * @param <T> value type
     * @param clazz class
     * @param consumerCallback consumerCallback
     */
    public <T> void subscribeSet4Json(Class<T> clazz, ConsumerCallback<Set<T>> consumerCallback) {
        this.subscribeProcess(new ConsumerCallback<byte[]>() {
            
            @Override
            public void process(String topic, long offset, int partition, byte[] message) {
                String jsonString = new String(message, Charsets.UTF_8);
                logger.debug("topic, offset = {}, value = {}", topic, offset, jsonString);
                Set<T> value = JsonUtils.toSet(jsonString, clazz);
                consumerCallback.process(topic, offset, partition, value);
            }
            
            @Override
            public void exceptions(String topic, long offset, int partition, byte[] message, Throwable ex) {
                String jsonString = new String(message, Charsets.UTF_8);
                logger.error("topic, offset = {}, value = {}", topic, offset, jsonString, ex);
                Set<T> value = JsonUtils.toSet(jsonString, clazz);
                consumerCallback.exceptions(topic, offset, partition, value, ex);
            }
        });
    }
    
    /**
     * subscribeSet4Json
     * 
     * @param <K> key
     * @param <V> value
     * @param keyClass keyClass
     * @param valueClass valueClass
     * @param consumerCallback consumerCallback
     */
    public <K, V> void subscribeMap4Json(Class<K> keyClass, Class<V> valueClass, ConsumerCallback<Map<K, V>> consumerCallback) {
        this.subscribeProcess(new ConsumerCallback<byte[]>() {
            
            @Override
            public void process(String topic, long offset, int partition, byte[] message) {
                String jsonString = new String(message, Charsets.UTF_8);
                logger.debug("topic, offset = {}, value = {}", topic, offset, jsonString);
                Map<K, V> value = JsonUtils.toMap(jsonString, keyClass, valueClass);
                consumerCallback.process(topic, offset, partition, value);
            }
            
            @Override
            public void exceptions(String topic, long offset, int partition, byte[] message, Throwable ex) {
                String jsonString = new String(message, Charsets.UTF_8);
                logger.error("topic, offset = {}, value = {}", topic, offset, jsonString, ex);
                Map<K, V> value = JsonUtils.toMap(jsonString, keyClass, valueClass);
                consumerCallback.exceptions(topic, offset, partition, value, ex);
            }
        });
    }
    
    /**
     * subscribe4Byte
     * 
     * @param consumerCallback consumerCallback
     */
    public void subscribe4Byte(ConsumerCallback<byte[]> consumerCallback) {
        this.subscribeProcess(consumerCallback);
    }
}
