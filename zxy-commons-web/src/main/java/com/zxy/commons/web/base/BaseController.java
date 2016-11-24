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
package com.zxy.commons.web.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base controller
 *
 * <p>
 * <a href="BaseController.java"><i>View Source</i></a>
 *
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("PMD")
public abstract class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 登出
     *
     * @return
     */
    protected void loginOut() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     *
     * @param key key
     * @param value value
     */
    protected void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

    /**
     * 获取登录的用户
     *
     * @param <T> session class type
     * @param clazz class
     * @return ShiroUser
     */
    protected <T> T getSession(Class<T> clazz) {
        T shiroUser = null;
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            shiroUser = (T) currentUser.getPrincipal();
        }
        return shiroUser;
    }
}
