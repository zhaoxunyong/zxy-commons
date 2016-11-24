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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogManagerImpl
 * 
 * <p>
 * <a href="LogManagerImpl.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class LogManagerImpl implements LogManager {
    private static Logger log = LoggerFactory.getLogger("profiler.speed.log");

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD")
    public String showTree(ExecNode root) {
        Locale locale = Locale.CHINA;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
        StringBuilder sb = new StringBuilder(sdf.format(new Date(root.getStartTime())) + "\n");
        long subTime = 0L;
        for (ExecNode child : root.getChildList()) {
            subTime += child.getExecTime();
        }
        sb.append("*");
        buildLog(sb, root.getExecTime(), root.getExecTime() - subTime, 100L, 0L, root.getMethodName());
        for (ExecNode child : root.getChildList()) {
            showNode(sb, 0, child, root);
        }
        String treeLog = sb.toString();
        log.info(treeLog);
        sb = null;
        sdf = null;
        locale = null;
        return treeLog;
    }

    @SuppressWarnings("PMD")
    private void showNode(StringBuilder sb, int level, ExecNode node, ExecNode root) {
        level++;
        long subTime = 0L;
        for (ExecNode child : node.getChildList()) {
            subTime += child.getExecTime();
        }
        sb.append("+");
        for (int i = 0; i < level; i++) {
            sb.append("---");
        }
        buildLog(sb, node.getExecTime(), node.getExecTime() - subTime,
                node.getExecTime() * 100L
                        / (node.getParent().getExecTime() == 0L ? 100L : node.getParent().getExecTime()),
                node.getStartTime() - root.getStartTime(), node.getMethodName());
        for (ExecNode child : node.getChildList()) {
            showNode(sb, level, child, root);
        }
    }

    private void buildLog(StringBuilder sb, long totalTime, long internalTime, long ratio, long startTimeFromRoot,
            String methodName) {
        sb.append("[" + totalTime + ", " + internalTime + ", " + ratio + "%, " + startTimeFromRoot + "]" + methodName
                + "\n");
    }
}
