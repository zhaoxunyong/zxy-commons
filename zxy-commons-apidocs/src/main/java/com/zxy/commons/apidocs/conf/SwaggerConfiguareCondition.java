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
package com.zxy.commons.apidocs.conf;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * SwaggerConfiguareCondition
 * <p>
 * <p>
 * <a href="SwaggerConfiguareCondition.java"><i>View Source</i></a>
 *
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class SwaggerConfiguareCondition implements Condition {
    @Override public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String enabled = context.getEnvironment().getProperty("apidocs.enabled");
        if(!StringUtils.isEmpty(enabled) && "false".equalsIgnoreCase(enabled)) {
            return false;
        }
//        String xmlEnabled = context.getEnvironment().getProperty("apidocs.use.xml.enabled");
//        if(!StringUtils.isEmpty(xmlEnabled) && "true".equalsIgnoreCase(xmlEnabled)) {
//            return false;
//        }
        try {
            // 是否有jar依赖, 没有的话, 不自动加载配置
            context.getClassLoader().loadClass(Docket.class.getName());
            return true;
        } catch (Exception ex){
            return false;
        }
    }
}