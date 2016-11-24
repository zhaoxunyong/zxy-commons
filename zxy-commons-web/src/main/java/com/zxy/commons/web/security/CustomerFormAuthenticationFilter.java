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
package com.zxy.commons.web.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxy.commons.json.JsonObject;

/**
 * 定制登陆授权过滤器
 * 
 * <p>
 * <a href="CustomerFormAuthenticationFilter.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class CustomerFormAuthenticationFilter extends FormAuthenticationFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 主要是针对登入成功的处理方法。对于请求头是AJAX的之间返回JSON字符串。
     */
    @Override
    @SuppressWarnings("PMD")
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
            ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {// 不是ajax请求
            issueSuccessRedirect(request, response);
        } else {
            httpServletResponse.setCharacterEncoding("UTF-8");
            write(httpServletResponse.getWriter(), true, "登录成功!");
        }
        return false;
    }

    /**
     * 登入失败
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ex, ServletRequest request,
            ServletResponse response) {
        if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {// 不是ajax请求
            setFailureAttribute(request, ex);
            return true;
        }
        try {
            response.setCharacterEncoding("UTF-8");
            String message = ex.getClass().getSimpleName();
            if ("IncorrectCredentialsException".equals(message)) {
                message = "密码错误!";
            } else if ("UnknownAccountException".equals(message)) {
                message = "账号不存在!";
            } else if ("LockedAccountException".equals(message)) {
                message = "账号被锁定!";
            } else {
                message = "未知错误!";
            }
            write(response.getWriter(), false, message);
        } catch (IOException e1) {
            logger.info(e1.getMessage());
        }
        return false;
    }

    /**
     * 所有请求都会经过的方法。
     */
    @Override
    @SuppressWarnings("PMD")
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (!isLoginRequest(request, response)) {
            if (logger.isTraceEnabled()) {
                logger.trace("Attempting to access a path which requires authentication.  Forwarding to the "
                        + "Authentication url [" + getLoginUrl() + "]");
            }
            if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {// 不是ajax请求
                saveRequestAndRedirectToLogin(request, response);
            } else {
                response.setCharacterEncoding("UTF-8");
                write(response.getWriter(), true, "您无权那样做，请尝试重新登陆或者重新授权!");
            }
            return false;
        }
        return true;
    }

    @SuppressWarnings("PMD")
    private void write(PrintWriter out, boolean flag, String msg) {
        try {
            JsonObject json = JsonObject.create()
                    .put("flag", flag)
                    .put("msg", msg);
            out.write(json.toJSONString());
            out.flush();
        } finally {
            if(out != null) {
                out.close();
            }
        }
    }

}
