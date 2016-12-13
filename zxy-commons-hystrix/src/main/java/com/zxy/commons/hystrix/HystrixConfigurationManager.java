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

import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;

/**
 * HystrixConfigurationManager
 * 
 * <p>
 * <a href="HystrixConfigurationManager.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@Configuration
@Conditional(HystrixAutoConfigureCondition.class)
@PropertySource(value="classpath:hystrix.properties", ignoreResourceNotFound=true)
public class HystrixConfigurationManager {
    private final static String ISOLATION_TIMEOUT_INMILLISECONDS = "execution.isolation.thread.timeoutInMilliseconds";
    private final static String CORE_SIZE = "coreSize";
    private final static String MAX_QUEUE_SIZE = "maxQueueSize";
    private final static String KEEP_ALIVETIME_MINUTES = "keepAliveTimeMinutes";
    private final static String QUEUE_SIZE_REJECTION_THRESHOLD = "queueSizeRejectionThreshold";
    
    private final static String EXECUTION_TIMEOUT_ENABLED = "execution.timeout.enabled";
    private final static String EXECUTION_INTERRUPTONTIMEOUT = "execution.isolation.thread.interruptOnTimeout";
    
    private final static String CIRCUITBREAKER_REQUEST_VOLUME_THRESHOLD = "circuitBreaker.requestVolumeThreshold";
    private final static String CIRCUITBREAKER_SLEEP_WINDOW_INMILLISECONDS = "circuitBreaker.sleepWindowInMilliseconds";
    private final static String CIRCUITBREAKER_ERROR_THRESHOLD_PERCENTAGE = "circuitBreaker.errorThresholdPercentage";
    private final static String CIRCUITBREAKER_FORCE_CLOSED = "circuitBreaker.forceClosed";
    private final static String CIRCUITBREAKER_ENABLED = "circuitBreaker.enabled";
    
    private final static String REQUESTCACHE_ENABLED = "requestCache.enabled";
    private final static String REQUESTLOG_ENABLED = "requestLog.enabled";
    
    private final static String METRICS_ROLLINGSTATS_NUMBUCKETS = "metrics.rollingStats.numBuckets";
    private final static String METRICS_ROLLINGSTATS_TIMEINMILLISECONDS = "metrics.rollingStats.timeInMilliseconds";
    private final static String METRICS_ROLLINGPERCENTILE_ENABLED = "metrics.rollingPercentile.enabled";
    

    
    @Autowired
    private Environment env;
    
    /**
     * hystirx init
     * 
     * @return Void
    */
    @Bean
//    @Scope(value = "singleton")
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
//        },
//                threadPoolProperties = {
//                        @HystrixProperty(name = "coreSize", value = "30"),
//                        @HystrixProperty(name = "maxQueueSize", value = "101"),
//                        @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
//                        @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
//                        @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
//                        @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
//        })
    public Void hystirxInit() {
        // http://blog.csdn.net/zheng0518/article/details/51713900
        AbstractConfiguration configuration = ConfigurationManager.getConfigInstance();
        // 表示所属的group，一个group共用线程池. 默认值：getClass().getSimpleName();
        // configuration.setProperty("groupKey", value);
        // 默认值：当前执行方法名
        // configuration.setProperty("commandKey", value);
        
        // 超时时间. 默认值：1000
        // 在THREAD模式下，达到超时时间，可以中断. 在SEMAPHORE模式下，会等待执行完成后，再去判断是否超时
        // 设置标准：有retry，99meantime+avg meantime. 没有retry，99.5meantime
        String isolationTimeoutInmilliseconds = env.getProperty(ISOLATION_TIMEOUT_INMILLISECONDS, "1000");
        configuration.setProperty(ISOLATION_TIMEOUT_INMILLISECONDS, isolationTimeoutInmilliseconds);
        
        // 线程池coreSize. 默认值：10 设置标准：qps*99meantime+breathing room
        String coreSize = env.getProperty(CORE_SIZE, String.valueOf(Runtime.getRuntime().availableProcessors() * 2));
        configuration.setProperty(CORE_SIZE, coreSize);
        // 请求等待队列. 默认值：-1 如果使用正数，队列将从SynchronizeQueue改为LinkedBlockingQueue
        String maxQueueSize = env.getProperty(MAX_QUEUE_SIZE, "-1");
        configuration.setProperty(MAX_QUEUE_SIZE, maxQueueSize);
        // 线程存存活时间。默认1分钟
        String keepAliveTimeMinutes = env.getProperty(KEEP_ALIVETIME_MINUTES, "1");
        configuration.setProperty(KEEP_ALIVETIME_MINUTES, keepAliveTimeMinutes);
        // 设置设个值的原因是maxQueueSize值运行时不能改变，我们可以通过修改这个变量动态修改允许排队的长度。默认5
        String queueSizeRejectionThreshold = env.getProperty(QUEUE_SIZE_REJECTION_THRESHOLD, "5");
        configuration.setProperty(QUEUE_SIZE_REJECTION_THRESHOLD, queueSizeRejectionThreshold);
        
        // 是否打开超时. 默认值：true
        String executionTimeoutEnabled = env.getProperty(EXECUTION_TIMEOUT_ENABLED, "true");
        configuration.setProperty(EXECUTION_TIMEOUT_ENABLED, executionTimeoutEnabled);
        // 当超时的时候是否中断(interrupt) HystrixCommand.run()执行
        String executionInterruptOnTimeout = env.getProperty(EXECUTION_INTERRUPTONTIMEOUT, "true");
        configuration.setProperty(EXECUTION_INTERRUPTONTIMEOUT, executionInterruptOnTimeout);
        // 10秒钟内至少20此请求失败，熔断器才发挥起作用. 默认值：20
        String circuitBreakerRequestVolumeThreshold = env.getProperty(CIRCUITBREAKER_REQUEST_VOLUME_THRESHOLD, "20");
        configuration.setProperty(CIRCUITBREAKER_REQUEST_VOLUME_THRESHOLD, circuitBreakerRequestVolumeThreshold);
        // 熔断器中断请求10秒后会进入半打开状态,放部分流量过去重试. 默认值：5000
        String circuitBreakerSleepWindowInMilliseconds = env.getProperty(CIRCUITBREAKER_SLEEP_WINDOW_INMILLISECONDS, "5000");
        configuration.setProperty(CIRCUITBREAKER_SLEEP_WINDOW_INMILLISECONDS, circuitBreakerSleepWindowInMilliseconds);
        // 失败率达到多少百分比后熔断. 默认值：50 主要根据依赖重要性进行调整
        String circuitBreakerErrorThresholdPercentage = env.getProperty(CIRCUITBREAKER_ERROR_THRESHOLD_PERCENTAGE, "50");
        configuration.setProperty(CIRCUITBREAKER_ERROR_THRESHOLD_PERCENTAGE, circuitBreakerErrorThresholdPercentage);
        // 是否强制关闭熔断 如果是强依赖，应该设置为true
        String circuitBreakerForceClosed = env.getProperty(CIRCUITBREAKER_FORCE_CLOSED, "false");
        configuration.setProperty(CIRCUITBREAKER_FORCE_CLOSED, circuitBreakerForceClosed);
        // 是否开启熔断，默认true
        String circuitBreakerEnabled = env.getProperty(CIRCUITBREAKER_ENABLED, "true");
        configuration.setProperty(CIRCUITBREAKER_ENABLED, circuitBreakerEnabled);
        
        // 设置是否缓存请求，request-scope内缓存。默认true
        String requestCacheEnabled = env.getProperty(REQUESTCACHE_ENABLED, "true");
        configuration.setProperty(REQUESTCACHE_ENABLED, requestCacheEnabled);
        // 设置HystrixCommand执行和事件是否打印到HystrixRequestLog中
        String requestLogEnabled = env.getProperty(REQUESTLOG_ENABLED, "true");
        configuration.setProperty(REQUESTLOG_ENABLED, requestLogEnabled);
        
        // 设置滑动统计的桶数量。默认10。metrics.rollingStats.timeInMilliseconds必须能被这个值整除。
        String metricsRollingStatsNumBuckets = env.getProperty(METRICS_ROLLINGSTATS_NUMBUCKETS, "10");
        configuration.setProperty(METRICS_ROLLINGSTATS_NUMBUCKETS, metricsRollingStatsNumBuckets);
        // 设置滑动窗口的统计时间。熔断器使用这个时间。默认10s
        String metricsRollingStatsTimeInMilliseconds = env.getProperty(METRICS_ROLLINGSTATS_TIMEINMILLISECONDS, "10000");
        configuration.setProperty(METRICS_ROLLINGSTATS_TIMEINMILLISECONDS, metricsRollingStatsTimeInMilliseconds);
        // 设置执行时间是否被跟踪，并且计算各个百分比，50%,90%等的时间。默认true。
        String metricsRollingPercentileEnabled = env.getProperty(METRICS_ROLLINGPERCENTILE_ENABLED, "true");
        configuration.setProperty(METRICS_ROLLINGPERCENTILE_ENABLED, metricsRollingPercentileEnabled);
        return null;
    }
    
    /**
     * hystrix aspect
     * 
     * @return HystrixCommandAspect
    */
    @Bean
    public HystrixCommandAspect hystrixAspect() {
        return new HystrixCommandAspect();
    }
}
