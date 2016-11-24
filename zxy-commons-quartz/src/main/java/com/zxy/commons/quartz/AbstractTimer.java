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
package com.zxy.commons.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时任务基础类
 * 
 * <p>
 * <a href="AbstractTimer.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
 */
public abstract class AbstractTimer extends QuartzJobBean {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 
     * 获取定时任务名
     *
     * @return 任务名
     */
    public abstract String getTimerName();

    /**
     * 
     * 执行任务
     *
     * @param context 上下文
     */
    public abstract void executeTask(JobExecutionContext context);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        long st = System.currentTimeMillis();
        logger.debug("[" + getTimerName() + "]定时任务启动......");
        try {
            executeTask(context);
        } catch (Exception e) {
            logger.error("[" + getTimerName() + "]定时任务执行失败: {}", e.getMessage(), e);
        }
        logger.debug("[" + getTimerName() + "]定时任务结束, 耗时" + (System.currentTimeMillis() - st)+"毫秒.");
    }

}
