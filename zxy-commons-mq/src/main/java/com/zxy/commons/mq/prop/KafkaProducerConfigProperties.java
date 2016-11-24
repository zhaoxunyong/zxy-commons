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
package com.zxy.commons.mq.prop;

import java.io.IOException;
import java.net.URL;

import com.zxy.commons.lang.conf.AbstractConfigProperties;

/**
 * KafkaProducerConfigProperties
 * 
 * <p>
 * <a href="KafkaProducerConfigProperties.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class KafkaProducerConfigProperties extends AbstractConfigProperties {

    private final static class KafkaProducerConfigPropertiesBuilder {
        private final static KafkaProducerConfigProperties INSTANCE = new KafkaProducerConfigProperties();
    }

    /**
     * 得到单例的KafkaProducerConfigProperties实例
     * 
     * @return 单例的KafkaProducerConfigProperties实例
     */
    public static KafkaProducerConfigProperties getInstance() {
        return KafkaProducerConfigPropertiesBuilder.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zxy.commons.lang.conf.AbstractConfigurator#getPath()
     */
    @Override
    protected URL getPath() throws IOException {
        return classpath2URL("kafka_producer.properties");
    }

    public String getMetadataBrokerList() {
        return getString("metadata.broker.list");
    }

    public String getRequestRequiredAcks() {
        return getString("request.required.acks");
    }

    public String getProducerType() {
        return getString("producer.type");
    }

    public String getBatchNumMessages() {
        return getString("batch.num.messages");
    }

    public String getQueueBufferingMaxMs() {
        return getString("queue.buffering.max.ms");
    }
}
