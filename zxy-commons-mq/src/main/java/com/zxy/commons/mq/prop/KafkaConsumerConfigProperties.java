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
 * KafkaConsumerConfigProperties
 * 
 * <p>
 * <a href="KafkaConsumerConfigProperties.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class KafkaConsumerConfigProperties extends AbstractConfigProperties {

    private final static class KafkaConsumerConfigPropertiesBuilder {
        private final static KafkaConsumerConfigProperties INSTANCE = new KafkaConsumerConfigProperties();
    }

    /**
     * 得到单例的KafkaConfigProperties实例
     * 
     * @return 单例的KafkaConfigProperties实例
     */
    public static KafkaConsumerConfigProperties getInstance() {
        return KafkaConsumerConfigPropertiesBuilder.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zxy.commons.lang.conf.AbstractConfigurator#getPath()
     */
    @Override
    protected URL getPath() throws IOException {
        return classpath2URL("kafka_consumer.properties");
    }

    public String getZookeeperConnect() {
        return getString("zookeeper.connect");
    }

    public String getZookeeperSessionTimeout() {
        return getString("zookeeper.session.timeout.ms");
    }

    public String getZookeeperSyncTime() {
        return getString("zookeeper.sync.time.ms");
    }

    /**
     * getAutoCommitEnable
     * @return autoCommitEnable
    */
    public String getAutoCommitEnable() {
        return getString("auto.commit.enable");
    }
    
    public String getAutoCommitInterval() {
        return getString("auto.commit.interval.ms");
    }
}
