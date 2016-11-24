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
package com.zxy.commons.exec;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.OS;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行系统命令类
 * 
 * <p>
 * <a href="CmdExecutor.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class CmdExecutor {
    private final static Logger LOGGER = LoggerFactory.getLogger(CmdExecutor.class);
    private final static String DEFAULT_WORK_HOME = "/tmp";
    private final static String PRE_CMD = OS.isFamilyWindows() ? "cmd.exe /C " : "";
    private final static String ENCODING = "UTF-8";
    
    private CmdExecutor() {}

    /**
     * 执行系统命令
     * 
     * @param command command
     * @return 是否启动成功
     * @throws InterruptedException InterruptedException
     * @throws IOException IOException
    */
    public static ExecutorResult exec(String command) throws InterruptedException, IOException {
        return exec(DEFAULT_WORK_HOME, command);
    }
    /**
     * 异步执行系统命令
     * 
     * @param command command
     * @throws InterruptedException InterruptedException
     * @throws IOException IOException
    */
    public static void execAsyc(String command) throws InterruptedException, IOException {
        execAsyc(DEFAULT_WORK_HOME, command);
    }
    
    /**
     * 执行系统命令
     * 
     * @param workHome workHome
     * @param command command
     * @return 是否启动成功
     * @throws InterruptedException InterruptedException
     * @throws IOException IOException
     */
    public static ExecutorResult exec(String workHome, String command) throws InterruptedException, IOException {
        CommandLine cmdLine = CommandLine.parse(PRE_CMD + command);
        Executor executor = new DefaultExecutor();
        executor.setWorkingDirectory(new File(workHome));
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();  
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();  
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream,errorStream);  
        executor.setStreamHandler(streamHandler);
         
        int code = executor.execute(cmdLine, EnvironmentUtils.getProcEnvironment());
        String successMsg = outputStream.toString(ENCODING);
        String errorMsg = errorStream.toString(ENCODING);
        return new ExecutorResult(code, successMsg, errorMsg);
    }
    
    /**
     * 异步执行系统命令
     * 
     * @param workHome workHome
     * @param command command
     * @throws InterruptedException InterruptedException
     * @throws IOException IOException
     */
    public static void execAsyc(String workHome, String command) throws InterruptedException, IOException {
        CommandLine cmdLine = CommandLine.parse(PRE_CMD + command);
        Executor executor = new DefaultExecutor();
        executor.setWorkingDirectory(new File(workHome));
        
        executor.setStreamHandler(new PumpStreamHandler(new LogOutputStream() {
            @Override
            protected void processLine(String line, int level) {
                LOGGER.debug(line);
            }
        }, new LogOutputStream() {
            @Override
            protected void processLine(String line, int level) {
                LOGGER.debug(line);
            }
        }));
         
        ExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        executor.execute(cmdLine, EnvironmentUtils.getProcEnvironment(), resultHandler);
        // resultHandler.waitFor();
    }
}
