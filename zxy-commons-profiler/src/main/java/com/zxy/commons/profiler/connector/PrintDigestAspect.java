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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxy.commons.profiler.connector.annotation.PrintLevel;
import com.zxy.commons.profiler.core.Profiler;
import com.zxy.commons.profiler.core.helper.PrintDigestHelper;
import com.zxy.commons.profiler.core.util.JoinPoinUtil;

/**
 * PrintDigestAspect
 * 
 * <p>
 * <a href="PrintDigestAspect.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@Aspect
public class PrintDigestAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * pointcut
    */
    @Pointcut("@within(com.zxy.commons.profiler.connector.annotation.PrintDigest)||@annotation(com.zxy.commons.profiler.connector.annotation.PrintDigest)")
    public void pointcut() {
        // do nothing
    }

    /**
     * Before advice
     * @param jp JoinPoint
    */
    @Before("pointcut()")
    public void beforeAdvice(JoinPoint jp) {
        try {
            Method method = JoinPoinUtil.getMethod(jp);
            String methodFullName = JoinPoinUtil.createMethodFullName(method);
            if (!PrintDigestHelper.isMethodNeedSkipPrint(method)) {
                Object[] args = PrintDigestHelper.getCanPrintArgs(method, jp.getArgs());

                PrintLevel printLevel = PrintDigestHelper.getPrintLevelIfNull(method, PrintLevel.INFO);

                Profiler.callMark(methodFullName, args, printLevel);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
