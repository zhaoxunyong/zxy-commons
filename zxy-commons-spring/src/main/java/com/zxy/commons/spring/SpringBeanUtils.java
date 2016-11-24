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
package com.zxy.commons.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** 
 * Spring工具类，用于从spring上下文中获取对象
 * 
 * <p>
 * 注意：
 * 此类一般用于非依赖注入的方式调用spring对象，建议尽量通过spring的依赖注入加载对象
 * <p>重要：
 * <p>使用时，需在spring中加入以下配置，以xml为例：
 * <p>&lt;bean id=&quot;springBeanUtils&quot; class=&quot;com.zxy.commons.spring.SpringBeanUtils&quot; /&gt;
 * <p>或者引入该包中的配置：
 * <p>&lt;import resource=&quot;classpath:spring_config/zxy_commons_spring.xml&quot; /&gt;
 * <p>
 * <a href="SpringBeanUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class SpringBeanUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext appContext)
            throws BeansException {
        if(context == null) {
            context = appContext;
        }
    }

    /**
     * 通过beanName从spring上下文中获取对象
     * 
     * @param beanName bean name
     * @return spring上下文中引用的对象
     */
    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }
    
    /**
     * 通过beanClass从spring上下文中获取对象
     * 
     * @param <T> This is the type parameter
     * @param beanClass bean class
     * @return spring上下文中引用的对象
    */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
    
    /**
     * 通过beanClass从spring上下文中获取对象
     * 
     * @param <T> This is the type parameter
     * @param name the name of the bean to retrieve
     * @param beanClass bean class
     * @return spring上下文中引用的对象。
    */
    public static <T> T getBean(String name, Class<T> beanClass) {
        return context.getBean(name, beanClass);
    }

    /**
     * 通过class type从spring上下文中获取对象
     * 
     * @param <T> This is the type parameter
     * @param type class type
     * @return spring上下文中引用的对象
     * @throws BeansException BeansException
     */
    public static <T> Object getBeanOfType(Class<T> type) throws BeansException {
        return BeanFactoryUtils.beanOfType(context, type);
    }
    
    /**
     * 初始化spring配置
     * 
     * @param applicationContextPaths Application context paths
     * @return Application context
     */
    public static ApplicationContext init(String... applicationContextPaths){
        return new ClassPathXmlApplicationContext(applicationContextPaths);
    }
    
    /*public static ClassPathXmlApplicationContext init(String... applicationContextPaths){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
//        context.setAllowBeanDefinitionOverriding(false);
        context.setConfigLocations(applicationContextPaths);
        context.refresh();
        return context;
    }*/


    /*private final static class SpringBeanUtilsBuilder {
        private final static SpringBeanUtils BUILDER = new SpringBeanUtils();
    }

    private SpringBeanUtils() {
        super();
    }

    @Override
    protected String getConfigLocation() {
        return "classpath:applicationContext.xml";
    }

    public static ApplicationContext getContext() {
        return SpringBeanUtilsBuilder.BUILDER.context;
    }*/
}
