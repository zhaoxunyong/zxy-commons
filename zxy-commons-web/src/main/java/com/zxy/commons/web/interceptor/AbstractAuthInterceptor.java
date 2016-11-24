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
package com.zxy.commons.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zxy.commons.lang.utils.RegexUtils;
import com.zxy.commons.lang.utils.StringsUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zxy.commons.lang.constant.CommonConstant;

/**
 * Mvc拦截器
 * 
 * http://blog.csdn.net/jrn1012/article/details/25781319
 * http://www.voidcn.com/blog/lablenet/article/p-4975165.html
 * 
 * <p>
 * <a href="AbstractAuthInterceptor.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@Component("SpringMVCInterceptor")
@Deprecated
public abstract class AbstractAuthInterceptor extends HandlerInterceptorAdapter {
    protected String excludePath;

    public void setExcludePath(String excludePath) {
        this.excludePath = excludePath;
    }

    protected abstract void sendRedirect(HttpServletRequest request);

    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html;charset=UTF-8");
        // 过滤登录、退出访问
        boolean beFilter = true;
        if(StringUtils.isNotBlank(excludePath)) {
            List<String> noFilters = StringsUtils.toList(excludePath);
            String uri = request.getRequestURI().replace(request.getContextPath(), "");
            for (String noFilter : noFilters) {
                if (RegexUtils.find(noFilter, uri)) {
                    beFilter = false;
                    break;
                }
            }
        }
        Object session = request.getSession().getAttribute(CommonConstant.SESSION_CONTEXT);
        if (beFilter && session != null) {
            // ajax方式交互
            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { // 如果是ajax请求响应头会有，x-requested-with；
                response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
                return false;
            }
            // 未登录
//            response.sendRedirect(request.getContextPath() + "/login/index");
            sendRedirect(request);
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
