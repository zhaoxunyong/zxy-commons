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
package com.zxy.commons.mybatis;


import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component; 

/** 
 * 主从数据库方法
 * 
 * <p>
 * <a href="SelectDataSourceAspect.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
*/
@Component
@Aspect
public class SelectDataSourceAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * aspect
    */
    @Pointcut("@annotation(com.zxy.commons.mybatis.SelectDataSource)")
    public void aspect(){
        // do nothing
    }
    
    /**
     * 设置本地使用的读数据库key值
     * 
     * @param point JoinPoint
    */
    @Before("aspect()")
    public void before(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();
//        Class<?>[] classz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method mt = target.getClass().getMethod(method, parameterTypes);
//            logger.info("mt.getName={}", mt.getName());
            if (mt != null && mt.isAnnotationPresent(SelectDataSource.class)) {
                SelectDataSource data = mt.getAnnotation(SelectDataSource.class);
                HandleDataSource.putDataSource(data.value());
            }else{
                HandleDataSource.clearCustomerType();
            }
            //logger.info("method.name={}, datasource.key={}", m.getName(), HandleDataSource.getDataSource());
        } catch (Exception e) {
            logger.error("before() error.", e);
            HandleDataSource.clearCustomerType();
        }
    }
    
    /**
     * 清除本地使用的读数据库key值
     * 
     * @param point JoinPoint
    */
    @After("aspect()")
    public void after(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();
        //logger.info("target={}, method={}", target.toString(), method);
//        Class<?>[] classz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method mt = target.getClass().getMethod(method, parameterTypes);
            //logger.debug("m.getName={}", m.getName());
            if (mt != null && mt.isAnnotationPresent(SelectDataSource.class)) {
                HandleDataSource.clearCustomerType();
            }
            //logger.info("method.name={}, datasource.key={}", m.getName(), HandleDataSource.getDataSource());
        } catch (Exception e) {
            logger.error("after() error.", e);
            HandleDataSource.clearCustomerType();
        }
    } 
} 
