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
package com.zxy.commons.logger.appender;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * RollingFileAppender类
 * 
 * <p>
 * <a href="RollingFileAppender.java"><i>View Source</i></a>
 *
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@SuppressFBWarnings
public class RollingFileAppender extends org.apache.log4j.RollingFileAppender {
    
    private String replace(String filePath, String property) {
        String file = filePath;
        String properties = System.getProperty(property);
        if (properties != null && !"".equals(properties)) {
            file = file.replace("${"+property+"}", properties);
        }
        file = file.replaceAll("\\s*", "");
        return file;
    }
    
    /**
     * Set file
     *
     * @param filePath filePath
     */
    public void setFile(String filePath) {
        String file = filePath;
        /*String taskName = System.getProperty("taskName");
        if (StringUtils.isNotBlank(taskName)) {
            file = file + "_" + taskName;
            file = file.replaceAll("\\s*", "");
        }*/
        file = replace(file, "AppId");
        file = replace(file, "ModuleId");
        file = replace(file, "TaskName");
        super.setFile(file);
    }
}
