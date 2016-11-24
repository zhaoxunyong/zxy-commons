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
package com.zxy.commons.web.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.zxy.commons.json.JsonObject;

/**
 * CustomMappingExceptionResolver
 * 
 * <p>
 * <a href="CustomMappingExceptionResolver.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class CustomMappingExceptionResolver extends SimpleMappingExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        logger.error(ex.getMessage(), ex);
        // Expose ModelAndView for chosen error view.
        String viewName = determineViewName(ex, request);
        if (viewName != null) {
            if (!(request.getHeader("accept").indexOf("application/json") > -1
                    || (request.getHeader("X-Requested-With") != null
                            && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
                // 如果不是异步请求
                // Apply HTTP status code for error views, if specified.
                // Only apply it if we're processing a top-level request.
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }
                return getModelAndView(viewName, ex, request);
            } else {// JSON格式返回
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    String msg = "服务器出现错误:"+ex.getMessage();
                    if (ex instanceof UnauthorizedException) {
                        msg = "您无权那样做，请尝试重新登陆或者重新授权!";
                    }
                    JsonObject json = JsonObject.create()
                            .put("flag", false)
                            .put("msg", msg);
                    out.write(json.toJSONString());
                    out.flush();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    if(out != null) {
                        out.close();
                    }
                }
                return null;
            }
        } else {
            return null;
        }
    }
}
