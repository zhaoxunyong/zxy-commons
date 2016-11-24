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
package com.zxy.commons.profiler.core.helper;

import java.lang.reflect.Method;

import com.zxy.commons.profiler.connector.annotation.PrintProfiler;

/**
 * PrintProfilerHelper
 * 
 * <p>
 * <a href="PrintProfilerHelper.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class PrintProfilerHelper {
    
    private PrintProfilerHelper() {}
    
    public static int getElapseTimeIfNull(Method method, int defaultTime) {
        int time = defaultTime;
        if (method.getDeclaringClass().isAnnotationPresent(PrintProfiler.class)) {
            time = ((PrintProfiler) method.getDeclaringClass().getAnnotation(PrintProfiler.class)).elapseTime();
        }
        if (method.isAnnotationPresent(PrintProfiler.class)) {
            time = ((PrintProfiler) method.getAnnotation(PrintProfiler.class)).elapseTime();
        }
        return time;
    }

    public static boolean isMethodExistsPrintProfiler(Method method) {
        boolean isClazzNeedProfiler = method.getDeclaringClass().isAnnotationPresent(PrintProfiler.class);
        boolean isMethodNeedProfiler = method.isAnnotationPresent(PrintProfiler.class);
        return (isClazzNeedProfiler) || (isMethodNeedProfiler);
    }
}
