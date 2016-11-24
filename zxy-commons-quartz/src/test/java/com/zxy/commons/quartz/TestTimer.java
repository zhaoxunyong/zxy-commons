/* 
 * Copyright (C), 2015-2016, 深圳天道计然金融服务有限公司
 * File Name: @(#)TestTimer.java
 * Encoding UTF-8
 * Author: zhaoxunyong@qq.com
 * Version: 1.0
 * Date: Jun 29, 2016
 */
package com.zxy.commons.quartz;

import org.quartz.JobExecutionContext;

/** 
 * Quartz test
 * 
 * <p>
 * <a href="TestTimer.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class TestTimer extends AbstractTimer {

    /* (non-Javadoc)
     * @see com.chinatime.datacenter.task.timer.AbstractTimer#getTimerName()
     */
    @Override
    public String getTimerName() {
        return "Quartz test";
    }

    /* (non-Javadoc)
     * @see com.chinatime.datacenter.task.timer.AbstractTimer#executeTask(org.quartz.JobExecutionContext)
     */
    @Override
    public void executeTask(JobExecutionContext context) {
        logger.info("executeTask......");
    }

}
