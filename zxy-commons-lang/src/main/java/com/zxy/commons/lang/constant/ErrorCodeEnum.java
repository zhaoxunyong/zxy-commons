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
package com.zxy.commons.lang.constant;

import com.zxy.commons.lang.exception.BaseException;
import com.zxy.commons.lang.exception.BusinessException;
import com.zxy.commons.lang.exception.DatasAccessException;
import com.zxy.commons.lang.exception.NotFoundException;
import com.zxy.commons.lang.exception.ParameterException;
import com.zxy.commons.lang.exception.UnknowException;

/**
 * 公共错误枚举对象
 * 
 * <p>
 * <a href="ErrorCodeEnum.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public enum ErrorCodeEnum {
    /**
     * 业务处理异常
     */
    BUSINESS_ERROR(900, "业务处理异常"), 
    
    
    /**
     * 参数传值异常
     */
    PARAMETER_ERROR(901, "参数传值异常"), 
    
    /**
     * 数据存储异常
     */
    DATA_ACCESS_ERROR(902, "数据存储异常"), 
    
    /**
     * 未知异常
     */
    UNKNOWN_ERROR(903, "未知异常"), 
    
    /**
     * 找不到数据
     */
    NOT_FOUND_ERROR(904, "找不到数据");

    private int code;
    private String msg;

    /**
     * @param code error code
     * @param msg error msg
     */
    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取错误码
     * 
     * @return 错误码
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取错误信息
     * 
     * @return 错误信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 转换为BaseException
     * 
     * @return BaseException
     */
    public BaseException toException() {
        switch (this) {
        case BUSINESS_ERROR:
            return new BusinessException(this.code, this.msg);
        case PARAMETER_ERROR:
            return new ParameterException(this.code, this.msg);
        case DATA_ACCESS_ERROR:
            return new DatasAccessException(this.code, this.msg);
        case UNKNOWN_ERROR:
            return new UnknowException(this.code, this.msg);
        case NOT_FOUND_ERROR:
            return new NotFoundException(this.code, this.msg);
        default:
            return new BusinessException(this.code, this.msg);
        }
    }
}
