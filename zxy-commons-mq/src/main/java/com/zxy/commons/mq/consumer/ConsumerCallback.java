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

/**
 * ConsumerCallback
 * 
 * <p>
 * <a href="ConsumerCallback.java"><i>View Source</i></a>
 * 
 * @param <T> Kafka object param.
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public interface ConsumerCallback<T> {
    
    /**
     * process
     * 
     * @param topic topic
     * @param offset offset
     * @param partition partition
     * @param message message.
    */
    void process(String topic, long offset, int partition, T message);
    
    /**
     * 异常处理
     * 
     * @param topic topic
     * @param offset offset
     * @param partition partition
     * @param message 原始的消息
     * @param ex Throwable
    */
    void exceptions(String topic, long offset, int partition, T message, Throwable ex);
}
