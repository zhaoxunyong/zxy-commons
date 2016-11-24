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

import com.google.common.base.Charsets;

/**
 * String consumer
 * 
 * <p>
 * <a href="StringConsumer.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class StringConsumer extends AbstractConsumer {

    /**
     * @param groupId
     * @param topic
     */
    private StringConsumer(String groupId, String topic) {
        super(groupId, topic);
    }

    /**
     * @param groupId
     * @param topic
     */
    private StringConsumer(String groupId, String topic, int threadNum) {
        super(groupId, topic, threadNum);
    }

    /**
     * decode
     * 
     * @param messages 消息
     * @param decoded string
     */
    private String decode(byte[] messages) {
        return new String(messages, Charsets.UTF_8);
    }
    
    /**
     * CreateConsumer
     * 
     * @param groupId groupId
     * @param topic topic
     * @param threadNum threadNum
     * @return StringConsumer
    */
    public static StringConsumer createConsumer(String groupId, String topic, int threadNum) {
        return new StringConsumer(groupId, topic, threadNum);
    }
    
    /**
     * CreateConsumer
     * 
     * @param groupId groupId
     * @param topic topic
     * @return StringConsumer
    */
    public static StringConsumer createConsumer(String groupId, String topic) {
        return new StringConsumer(groupId, topic);
    }
    
    /**
     * subscribe4Kryo
     * 
     * @param consumerCallback consumerCallback
     */
    public void subscribe(ConsumerCallback<String> consumerCallback) {
        this.subscribeProcess(new ConsumerCallback<byte[]>() {
            
            @Override
            public void process(String topic, long offset, int partition, byte[] message) {
                String value = decode(message);
                logger.debug("topic, offset = {}, partition = {}, value = {}", topic, offset, partition, value);
                consumerCallback.process(topic, offset, partition, value);
            }
            
            @Override
            public void exceptions(String topic, long offset, int partition, byte[] message, Throwable ex) {
                String value = decode(message);
                logger.error("topic, offset = {}, partition = {}, value = {}", topic, offset, partition, value, ex);
                consumerCallback.exceptions(topic, offset, partition, value, ex);
            }
        });
    }
    
}
