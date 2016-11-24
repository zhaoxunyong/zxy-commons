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
package com.zxy.commons.profiler.core.callmonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxy.commons.profiler.connector.annotation.PrintLevel;

/**
 * CallMarkManagerImpl
 * 
 * <p>
 * <a href="CallMarkManagerImpl.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class CallMarkManagerImpl implements CallMarkManager {
    private static Logger log = LoggerFactory.getLogger("profiler.callMark.log");

    /**
     * {@inheritDoc}
     */
    public void showCallMark(CallMark callMark) {
        log.info(callMark.toString());
    }

    /**
     * {@inheritDoc}
     */
    public void showCallMark(CallMark callMark, PrintLevel printLevel) {
        if (printLevel == PrintLevel.DEBUG) {
            log.debug(callMark.toString());
        } else if (printLevel == PrintLevel.INFO) {
            log.info(callMark.toString());
        } else if (printLevel == PrintLevel.WARN) {
            log.warn(callMark.toString());
        } else if (printLevel == PrintLevel.ERROR) {
            log.error(callMark.toString());
        }
    }
}
