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

import javax.annotation.PostConstruct;

import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import com.zxy.commons.web.constant.ShiroConstant;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登录和授权
 * 
 * <p>
 * <a href="AbstractCustomerShiroDbRealm.java"><i>View Source</i></a>
 *
 * @param <T> session class type
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
//@Transactional(readOnly = true)
public abstract class AbstractCustomerShiroDbRealm<T> extends AuthorizingRealm {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * session检查
     * <p>
     * 检查用户名与密码是否正确? <br />
     * 正确: 返回需要缓存的session对象 <br />
     * 错误: 抛出{@code org.apache.shiro.authz.AuthorizationInfo}异常
     *
     * @param username username
     * @param password password
     * @return 需要缓存的session对象
     * @throws AuthenticationException AuthenticationException
     */
    protected abstract T sessionCheck(String username, String password) throws AuthenticationException;

    /**
     * 添加角色权限到shiro中
     *
     * @param simpleAuthorizationInfo simpleAuthorizationInfo
     * @param sessionObject sessionObject
     */
    protected abstract void addRoleAndPermissions(SimpleAuthorizationInfo simpleAuthorizationInfo, T sessionObject);
    
    /**
     * 认证回调函数,登录时调用.
     */
//    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        Preconditions.checkArgument(authcToken instanceof UsernamePasswordToken, "AuthenticationToken is not UsernamePasswordToken instance.");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 获得用户名与密码
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        T sessionObject = sessionCheck(username, password);
        String decodePassword = Hashing.sha1().hashBytes(String.valueOf(token.getPassword()).getBytes()).toString();
        return new SimpleAuthenticationInfo(sessionObject, decodePassword.toCharArray(), getName());

    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
//    @Transactional(readOnly = true)
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        @SuppressWarnings("unchecked")
        T sessionObject = (T)principals.getPrimaryPrincipal();
        // 授权只有一次
//        if (!shiroUser.isHasFillData()) {
//            checkAndFillData(shiroUser);
//        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        // 基于Role的权限信息
//        info.addRole(shiroUser.getEmpJob());// 职位理解成角色
//        // 基于Permission的权限信息
//        info.addStringPermissions(shiroUser.getPermissions());
        addRoleAndPermissions(info, sessionObject);
        return info;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ShiroConstant.HASH_ALGORITHM);
        matcher.setHashIterations(ShiroConstant.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }
}
