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

import java.util.Calendar;

import com.zxy.commons.profiler.connector.annotation.PrintLevel;
import com.zxy.commons.profiler.core.callmonitor.CallMark;
import com.zxy.commons.profiler.core.callmonitor.CallMarkManager;
import com.zxy.commons.profiler.core.callmonitor.CallMarkManagerImpl;

/**
 * Profiler
 * 
 * <p>
 * <a href="Profiler.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class Profiler {
    private static final ThreadLocal<ExecData> DATAHOLDER = new ThreadLocal<>();
    private static final int ELAPSE_TIME_MS_TO_LOG = 500;
    private static LogManager logManager = new LogManagerImpl();
    private static CallMarkManager callMarkManager = new CallMarkManagerImpl();
    
    private Profiler() {}
    /**
     * Profiler start
     * 
     * @param logName logName
    */
    public static void start(String logName) {
        start(logName, ELAPSE_TIME_MS_TO_LOG);
    }
    
    /**
     * Profiler start
     * 
     * @param logName logName
     * @param elapseTimeMsToLog elapseTimeMsToLog
    */
    public static void start(String logName, int elapseTimeMsToLog) {
        ExecData execData = (ExecData) DATAHOLDER.get();
        ExecNode currentNode = new ExecNode(logName, System.currentTimeMillis(), elapseTimeMsToLog);
        if (execData == null) {
            execData = new ExecData();
            execData.root = currentNode;
            DATAHOLDER.set(execData);
        } else {
            ExecNode parent = execData.currentNode;
            currentNode.setParent(parent);
            parent.getChildList().add(currentNode);
        }
        execData.currentNode = currentNode;
        execData.level += 1;
    }

    /**
     * stop
    */
    public static void stop() {
        ExecData execData = (ExecData) DATAHOLDER.get();
        long nowTime = System.currentTimeMillis();
        execData.currentNode.setEndTime(nowTime);
        ExecNode child = execData.currentNode;
        execData.currentNode = child.getParent();
        execData.level -= 1;
        if (execData.level == 0) {
            if ((nowTime - execData.root.getStartTime() >= execData.root.getElapseTimeMsToLog())
                    || (execData.showflag)) {
                logManager.showTree(execData.root);
            }
            DATAHOLDER.remove();
        } else if (child.getExecTime() >= child.getElapseTimeMsToLog()) {
            execData.showflag = true;
        }
    }

    /**
     * callMark
     * 
     * @param methodFullName methodFullName
     * @param args object args
     * @param printLevel printLevel
    */
    public static void callMark(String methodFullName, Object[] args, PrintLevel printLevel) {
        CallMark callMark = new CallMark(Calendar.getInstance(), methodFullName, args);

        callMarkManager.showCallMark(callMark, printLevel);
    }

    /**
     * callMark
     * 
     * @param methodFullName methodFullName
    */
    public static void callMark(String methodFullName) {
        CallMark callMark = new CallMark(Calendar.getInstance(), methodFullName);

        callMarkManager.showCallMark(callMark);
    }
}
