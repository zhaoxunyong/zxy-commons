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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.zxy.commons.profiler.connector.annotation.PrintDigest;
import com.zxy.commons.profiler.connector.annotation.PrintLevel;
import com.zxy.commons.profiler.connector.annotation.SkipDigest;

/**
 * PrintDigestHelper
 * 
 * <p>
 * <a href="PrintDigestHelper.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class PrintDigestHelper {
    
    private PrintDigestHelper() {}
    
    public static PrintLevel getPrintLevelIfNull(Method method, PrintLevel defaultLevel) {
        PrintLevel printLevel = defaultLevel;
        if (method.getDeclaringClass().isAnnotationPresent(PrintDigest.class)) {
            printLevel = ((PrintDigest) method.getDeclaringClass().getAnnotation(PrintDigest.class)).printLevel();
        }
        if (method.isAnnotationPresent(PrintDigest.class)) {
            printLevel = ((PrintDigest) method.getAnnotation(PrintDigest.class)).printLevel();
        }
        return printLevel;
    }

    public static boolean isMethodExistsPrintDigest(Method method) {
        boolean isClazzChangePrintLevel = method.getDeclaringClass().isAnnotationPresent(PrintDigest.class);

        boolean isMethodChangePrintLevel = method.isAnnotationPresent(PrintDigest.class);
        return (isClazzChangePrintLevel) || (isMethodChangePrintLevel);
    }

    public static boolean isMethodNeedSkipPrint(Method method) {
        Class<?> declaringClazz = method.getDeclaringClass();

        boolean isMethodNeedSkipPrint = method.isAnnotationPresent(SkipDigest.class);

        boolean isClazzNeedSkipPrint = declaringClazz.isAnnotationPresent(SkipDigest.class);
        return (isMethodNeedSkipPrint) || (isClazzNeedSkipPrint);
    }

    public static Object[] getCanPrintArgs(Method method, Object[] arguments) {
        List<Object> argsList = new ArrayList<>();
        if (null == arguments) {
            return argsList.toArray();
        }
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (null != parameterAnnotations) {
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] annotations = parameterAnnotations[i];
                if (!hasSkipPrint(annotations)) {
                    argsList.add(arguments[i]);
                }
            }
        }
        return argsList.toArray();
    }

    private static boolean hasSkipPrint(Annotation[] annotations) {
        if (null == annotations) {
            return false;
        }
        boolean hasSkipPrint = false;
        for (int j = 0; j < annotations.length; j++) {
            if (annotations[j].annotationType() == SkipDigest.class) {
                hasSkipPrint = true;
            }
        }
        return hasSkipPrint;
    }
}
