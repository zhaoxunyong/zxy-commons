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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxy.commons.mq.consumer.ConsumerCallback;
import com.zxy.commons.mq.consumer.StringConsumer;

/** 
 * Consumer test
 * 
 * <p>
 * <a href="ConsumerTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class ConsumerTest {
    private static final String GROUP_ID = "KafkaUtils-test-topic-group";
    private static final String TOPIC = "KafkaUtils-test-topic";
    private final static Logger LOGGER = LoggerFactory.getLogger(StringConsumerTest.class);
    
    @Test
    public void consumer() {
        StringConsumer.createConsumer(GROUP_ID, TOPIC).subscribe(new ConsumerCallback<String>() {

            @Override
            public void process(String topic, long offset, int partition, String message) {
                LOGGER.info("message=========="+message);
            }

            @Override
            public void exceptions(String topic, long offset, int partition, String message, Throwable ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        });
    }
}
