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
package com.zxy.commons.profiler.core.util;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * JoinPoinUtil
 * 
 * <p>
 * <a href="JoinPoinUtil.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class JoinPoinUtil {
    
    private JoinPoinUtil() {}
    
    /**
     * Create method fullName
     * 
     * @param method Method
     * @return methodFullName
    */
    public static String createMethodFullName(Method method) {
        Class<?> declaringClazz = method.getDeclaringClass();
        String pageName = declaringClazz.getPackage().getName();
        return pageName + "." + declaringClazz.getSimpleName() + "." + method.getName();
    }

    /**
     * Get method
     * @param jp JoinPoint
     * @return Method
     * @throws NoSuchMethodException NoSuchMethodException
    */
    public static Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        return getTargetClass(jp).getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    /**
     * Get target class
     * 
     * @param jp JoinPoint
     * @return Target class
     * @throws NoSuchMethodException NoSuchMethodException
    */
    public static Class<? extends Object> getTargetClass(JoinPoint jp) throws NoSuchMethodException {
        return jp.getTarget().getClass();
    }
}
