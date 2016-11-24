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

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.zxy.commons.lang.utils.ObjectsUtils;

/**
 * CallMark
 * 
 * <p>
 * <a href="CallMark.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class CallMark implements Serializable {
    private static final long serialVersionUID = 1L;
    private Calendar time;
    private String method;
    private List<Object> args;

    public CallMark(Calendar time, String method, Object[] args) {
        this.time = time;
        this.method = method;
        if(args != null && args.length > 0) {
            this.args = Arrays.asList(args);
        }
    }

    public CallMark(Calendar time, String method) {
        this(time, method, null);
    }

    public Calendar getTime() {
        return this.time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD")
    public String toString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        StringBuilder sb = new StringBuilder();
        sb.append("callMark:{");
        if (this.time == null) {
            sb.append("time:null");
        } else {
            sb.append("time:" + df.format(this.time.getTime()));
        }
        sb.append(",");
        sb.append("method:" + this.method);
        if (null != this.args) {
            sb.append(",");
            sb.append("args:");
            sb.append(ObjectsUtils.toString(this.args));
        }
        sb.append("}");
        return sb.toString();
        // return ReflectionToStringBuilder.toString(this,
        // ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
