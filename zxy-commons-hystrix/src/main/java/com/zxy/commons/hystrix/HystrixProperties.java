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
package com.zxy.commons.hystrix;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/** 
 * HystrixProperties
 * 
 * <p>
 * <a href="HystrixProperties.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
@Component
@PropertySource(value="classpath:hystrix.properties", ignoreResourceNotFound=true)
public class HystrixProperties {
    public final static String HYSTRIX_ENABLED = "hystrix.enabled";
    public final static String HYSTRIX_STREAM_ENABLED = "hystrix.stream.enabled";
    public final static String HYSTRIX_STREAM_HOST = "hystrix.stream.host";
    public final static String HYSTRIX_STREAM_PORT = "hystrix.stream.port";
    
    public final static String ISOLATION_TIMEOUT_INMILLISECONDS = "execution.isolation.thread.timeoutInMilliseconds";
    public final static String CORE_SIZE = "coreSize";
    public final static String MAX_QUEUE_SIZE = "maxQueueSize";
    public final static String KEEP_ALIVETIME_MINUTES = "keepAliveTimeMinutes";
    public final static String QUEUE_SIZE_REJECTION_THRESHOLD = "queueSizeRejectionThreshold";
    
    public final static String EXECUTION_TIMEOUT_ENABLED = "execution.timeout.enabled";
    public final static String EXECUTION_INTERRUPTONTIMEOUT = "execution.isolation.thread.interruptOnTimeout";
    
    public final static String CIRCUITBREAKER_REQUEST_VOLUME_THRESHOLD = "circuitBreaker.requestVolumeThreshold";
    public final static String CIRCUITBREAKER_SLEEP_WINDOW_INMILLISECONDS = "circuitBreaker.sleepWindowInMilliseconds";
    public final static String CIRCUITBREAKER_ERROR_THRESHOLD_PERCENTAGE = "circuitBreaker.errorThresholdPercentage";
    public final static String CIRCUITBREAKER_FORCE_CLOSED = "circuitBreaker.forceClosed";
    public final static String CIRCUITBREAKER_ENABLED = "circuitBreaker.enabled";
    
    public final static String REQUESTCACHE_ENABLED = "requestCache.enabled";
    public final static String REQUESTLOG_ENABLED = "requestLog.enabled";
    
    public final static String METRICS_ROLLINGSTATS_NUMBUCKETS = "metrics.rollingStats.numBuckets";
    public final static String METRICS_ROLLINGSTATS_TIMEINMILLISECONDS = "metrics.rollingStats.timeInMilliseconds";
    public final static String METRICS_ROLLINGPERCENTILE_ENABLED = "metrics.rollingPercentile.enabled";


    @Autowired
    private Environment env;


    /**
     * @return the env
     */
    public Environment getEnv() {
        return env;
    }


    /**
     * @param env the env to set
     */
    public void setEnv(Environment env) {
        this.env = env;
    }
    
    /**
     * 是否有启动hystrix功能
     * 
     * @return 是否有启动
    */
    public boolean hystrixEnabled() {
        String enabled = env.getProperty(HYSTRIX_ENABLED);
        if(StringUtils.isNotBlank(enabled) && "false".equalsIgnoreCase(enabled)) {
            return false;
        }
        return true;
    }
    
    /**
     * 是否有启动hystrix strem功能
     * 
     * @return 是否有启动
    */
    public boolean hystrixStreamEnabled() {
        String enabled = env.getProperty(HYSTRIX_STREAM_ENABLED);
        if(StringUtils.isNotBlank(enabled) && "false".equalsIgnoreCase(enabled)) {
            return false;
        }
        return true;
    }
    
    /**
     * get hystrix stream host
     * 
     * @return host
    */
    public String getHystrixStreamHost() {
        return env.getProperty(HYSTRIX_STREAM_HOST, "");
    }
    
    /**
     * get hystrix stream port
     * 
     * @return port
     * @throws IOException IOException
    */
    public int getHystrixStreamPort() throws IOException {
        String portStr = env.getProperty(HYSTRIX_STREAM_PORT);
        if(StringUtils.isBlank(portStr)) {
            ServerSocket socket = new ServerSocket(0);
            try {
                return socket.getLocalPort();
            } finally {
                socket.close();
            }
        }
        return Integer.parseInt(portStr);
    }
    
}
