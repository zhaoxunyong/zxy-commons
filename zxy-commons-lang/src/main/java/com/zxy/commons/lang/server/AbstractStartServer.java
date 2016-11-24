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
package com.zxy.commons.lang.server;

import static java.util.Arrays.asList;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.ServiceManager;

/**
 * AbstractStartServer
 * 
 * <p>
 * <a href="AbstractStartServer.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractStartServer extends AbstractService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 服务开始
     */
    protected void startServer() {
        final ServiceManager manager = new ServiceManager(asList(this));
        RecordingListener listener = new RecordingListener();
        manager.addListener(listener);
        logger.info("Starting server...");
        manager.startAsync().awaitHealthy();
        logger.info("server has started successfully.");
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("Stopping server...");
                    manager.stopAsync().awaitStopped(10, TimeUnit.SECONDS);
                    logger.info("server has stopped successfully.");
                } catch (Exception timeout) {
                    logger.warn(timeout.getMessage(), timeout);
                }
            }
        }));
    }

    /**
     * 服务启动
     */
    protected abstract void start();

    /**
     * 服务停止时的一些操作 注意：如果kill -9的话，是不会执行该方法的
     */
    protected abstract void stop();

    @Override
    protected void doStart() {
        start();
        notifyStarted();
    }

    @Override
    protected void doStop() {
        stop();
        notifyStopped();
    }
}
