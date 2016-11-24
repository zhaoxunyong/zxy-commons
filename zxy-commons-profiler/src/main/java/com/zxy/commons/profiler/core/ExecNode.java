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
package com.zxy.commons.profiler.core;

import java.util.ArrayList;
import java.util.List;

/**
 * ExecNode
 * 
 * <p>
 * <a href="ExecNode.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class ExecNode {
    private String methodName;
    private long startTime;
    private long endTime;
    private long execTime;
    private ExecNode parent;
    private List<ExecNode> childList = new ArrayList<>();
    private long elapseTimeMsToLog;

    public ExecNode(String methodName, long startTime, long elapseTimeMsToLog) {
        this.methodName = methodName;
        this.startTime = startTime;
        this.elapseTimeMsToLog = elapseTimeMsToLog;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    /**
     * setEndTime 
     * 
     * @param endTime endTime
    */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
        this.execTime = (endTime - this.startTime);
    }

    public List<ExecNode> getChildList() {
        return this.childList;
    }

    public void setChildList(List<ExecNode> childList) {
        this.childList = childList;
    }

    public ExecNode getParent() {
        return this.parent;
    }

    public void setParent(ExecNode parent) {
        this.parent = parent;
    }

    public long getExecTime() {
        return this.execTime;
    }

    public long getElapseTimeMsToLog() {
        return this.elapseTimeMsToLog;
    }

    public void setElapseTimeMsToLog(long elapseTimeMsToLog) {
        this.elapseTimeMsToLog = elapseTimeMsToLog;
    }
}
