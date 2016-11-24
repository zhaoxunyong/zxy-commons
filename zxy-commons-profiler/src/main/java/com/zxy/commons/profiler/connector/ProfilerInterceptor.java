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

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import com.zxy.commons.profiler.core.Profiler;
import com.zxy.commons.profiler.core.helper.PrintProfilerHelper;

/**
 * ProfilerInterceptor
 * 
 * <p>
 * <a href="ProfilerInterceptor.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class ProfilerInterceptor extends AbstractMonitoringInterceptor {
    private static final long serialVersionUID = 7406883880062745237L;
    private int monitorTime;
    
    /**
     * 
     */
    public ProfilerInterceptor() {
        super();
    }

    public ProfilerInterceptor(boolean useDynamicLogger) {
        super();
        setUseDynamicLogger(useDynamicLogger);
    }

    protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
        return true;
    }

    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
        String name = createInvocationTraceName(invocation);

        boolean isExistsPrintProfiler = PrintProfilerHelper.isMethodExistsPrintProfiler(invocation.getMethod());
        try {
            if (!isExistsPrintProfiler) {
                Profiler.start(name, this.monitorTime);
            }
            return invocation.proceed();
        } finally {
            if (!isExistsPrintProfiler) {
                Profiler.stop();
            }
        }
    }

    public int getMonitorTime() {
        return this.monitorTime;
    }

    public void setMonitorTime(int monitorTime) {
        this.monitorTime = monitorTime;
    }
}
