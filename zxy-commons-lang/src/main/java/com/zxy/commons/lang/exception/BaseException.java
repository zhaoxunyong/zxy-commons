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
package com.zxy.commons.lang.exception;

/**
 * 异常基类
 * 
 * <p>
 * <a href="BaseException.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("PMD.AbstractNaming")
public abstract class BaseException extends RuntimeException {
    private static final long serialVersionUID = 8701788553318741599L;
    protected int code;

    /**
     * 构造函数
     * 
     * @param code 错误代码
     */
    public BaseException(int code) {
        super("");
        this.code = code;
    }

    /**
     * 构造函数
     * 
     * @param msg 错误信息
     */
    public BaseException(String msg) {
        super(msg);
    }

    /**
     * 构造函数
     * 
     * @param code 错误代码
     * @param msg 错误信息
     */
    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 构造函数
     * 
     * @param cause Throwable cause
     */
    public BaseException(BaseException cause) {
        this(cause.getCode(), cause.getMessage());
    }

    /**
     * 构造函数
     * 
     * @param cause Throwable cause
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     * 
     * @param msg 错误信息
     * @param cause Throwable cause
     */
    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 构造函数
     * 
     * @param code code
     * @param cause Throwable cause
     */
    public BaseException(int code, Throwable cause) {
        super("", cause);
        this.code = code;
    }

    /**
     * 构造函数
     * 
     * @param code 错误代码
     * @param msg 错误信息
     * @param cause Throwable cause
     */
    public BaseException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    /**
     * 获取错误码
     * 
     * @return abstract
     */
    public int getCode() {
        return code;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String simpleName = getClass().getName();
        String message = getLocalizedMessage();
        String msg = (message != null) ? (simpleName + ": " + message) : simpleName;
        return code != 0 ? ("code: " +code + ", message: " + msg) : msg;
    }
}
