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
package com.zxy.commons.jetty.exception;

/** 
 * ServerErrorException
 * 
 * <p>
 * <a href="ServerErrorException.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class ServerErrorException extends RuntimeException {
    private static final long serialVersionUID = 4757133834577128032L;
    
    /**
     * 构造函数
     * 
     * @param cause Throwable异常
     */
    public ServerErrorException(Throwable cause) {
        super(cause);
    }
    
    /**
     * 构造函数
     * 
     * @param message message
     * @param cause Throwable异常
     */
    public ServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
