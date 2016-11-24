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
package com.zxy.commons.profiler.connector;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxy.commons.profiler.core.Profiler;
import com.zxy.commons.profiler.core.helper.PrintProfilerHelper;
import com.zxy.commons.profiler.core.util.JoinPoinUtil;

/**
 * PrintProfilerAspect
 * 
 * <p>
 * <a href="PrintProfilerAspect.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@Aspect
public class PrintProfilerAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * pointcut
    */
    @Pointcut("@within(com.zxy.commons.profiler.connector.annotation.PrintProfiler)||@annotation(com.zxy.commons.profiler.connector.annotation.PrintProfiler)")
    public void pointcut() {
        // do nothing
    }

    /**
     * Around advice
     * @param pjp ProceedingJoinPoint
     * @return Object
     * @throws Throwable Throwable
    */
    @Around("pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        String methodFullName = "unkonwMethod()";
        int monitorTime = 500;
        try {
            Method method = JoinPoinUtil.getMethod(pjp);
            methodFullName = JoinPoinUtil.createMethodFullName(method);
            monitorTime = PrintProfilerHelper.getElapseTimeIfNull(method, monitorTime);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            Profiler.start(methodFullName, monitorTime);
            return pjp.proceed();
        } finally {
            Profiler.stop();
        }
    }
}
