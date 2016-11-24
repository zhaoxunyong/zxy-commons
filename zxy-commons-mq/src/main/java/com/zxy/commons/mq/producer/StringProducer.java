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

import com.zxy.commons.mq.serialize.StringSerialize;

/** 
 * String producer
 * 
 * <p>
 * <a href="StringProducer.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
@SuppressWarnings("deprecation")
public class StringProducer extends AbstractProducer<String, String> {
    private final static class StringProducerBuilder {
        private final static StringProducer BUILDER = new StringProducer(null);
    }
//    private final static class AsyncStringProducerBuilder {
//        private final static StringProducer BUILDER = new StringProducer(ProducerType.async);
//    }
//    private final static class SyncStringProducerBuilder {
//        private final static StringProducer BUILDER = new StringProducer(ProducerType.sync);
//    }
    
    private StringProducer(ProducerType producerType){
        super(producerType);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getValueSerializerClass() {
        return StringSerialize.class;
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
     * <p>
     * 通过kafka_producer.properties的producer.type决定是同步还是异步
     * 
     * @param topic topic 
     * @param routerKey 路由key，为空时会随机写入 
     * @param value value
    */
    public static void producer(String topic, String routerKey, String value) {
        StringProducerBuilder.BUILDER.sendMessage(topic, routerKey, value);
    }
    
//    /**
//     * Sync producer message
//     * 
//     * <p>
//     * 会覆盖kafka_producer.properties的producer.type配置
//     * 
//     * @param topic topic 
//     * @param routerKey 路由key，为空时会随机写入 
//     * @param value value
//    */
//    public static void producer4Sync(String topic, String routerKey, String value) {
//        SyncStringProducerBuilder.BUILDER.sendMessage(topic, routerKey, value);
//    }
//    
//    /**
//     * Async producer message
//     * 
//     * <p>
//     * 会覆盖kafka_producer.properties的producer.type配置
//     * 
//     * @param topic topic 
//     * @param routerKey 路由key，为空时会随机写入 
//     * @param value value
//    */
//    public static void producer4Async(String topic, String routerKey, String value) {
//        AsyncStringProducerBuilder.BUILDER.sendMessage(topic, routerKey, value);
//    }
    
}
