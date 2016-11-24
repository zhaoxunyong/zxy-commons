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
package com.zxy.commons.dubbo;

import java.util.UUID;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.RpcContext;

/** 
 * Dubbo trace interceptor
 * 
 * <p>
 * <a href="TraceInterceptor.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class TraceInterceptor extends AbstractMonitoringInterceptor {
    private static final long serialVersionUID = 7242522047049878874L;
    private final static String TRACE_ID = "traceId";
    
    public TraceInterceptor() {
        super();
    }
    
    public TraceInterceptor(boolean useDynamicLogger) {
        setUseDynamicLogger(useDynamicLogger);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
//        String name = createInvocationTraceName(invocation);
        String invocationDescription = getInvocationDescription(invocation);
        logger.trace("Entering " + invocationDescription);
        RpcContext rpcContext = RpcContext.getContext();
        if(rpcContext != null) {
            String traceId = rpcContext.getAttachment(TRACE_ID);
            if(StringUtils.isNotEmpty(traceId)) {
                logger.trace(invocationDescription+", traceId: "+traceId);
            } else {
                traceId = UUID.randomUUID().toString().replace("-", "");
                logger.trace(invocationDescription+", traceId is empty, auto generated, traceId: " + traceId);
            }
            rpcContext.setAttachment(TRACE_ID, traceId);
        }
        Object rval = invocation.proceed();
        logger.trace("Exiting " + invocationDescription);
        return rval;
    }
    
    /**
     * Return a description for the given method invocation.
     * @param invocation the invocation to describe
     * @return the description
     */
    protected String getInvocationDescription(MethodInvocation invocation) {
        return "method '" + invocation.getMethod().getName() + "' of class [" +
                invocation.getThis().getClass().getName().replaceAll("\\$\\$EnhancerBySpringCGLIB\\$\\$.+$", "") + "]";
    }
}
