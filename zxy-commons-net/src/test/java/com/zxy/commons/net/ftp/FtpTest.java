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
package com.zxy.commons.net.ftp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * FtpTest
 * 
 * <p>
 * <a href="FtpTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class FtpTest {
    /**
     * Set up
     * 
     * @throws Exception
     */
    @Before
    public void setUp() {
        // do nothing
    }

    /**
     * Tear down
     */
    @After
    public void tearDown() {
        // do nothing
    }

    @Test
    @SuppressWarnings("PMD")
    public void ftp() {
        String host = "";
        int port = 21;
        String username = "";
        String password = "";
        int connectTimeoutMs = 5000;
//        long keepAliveTimeoutSeconds = 30L;
//        FTPClientConfig ftpClientConfig = new FTPClientConfig();
        FtpConfig ftpConfig = FtpConfig.builder(host)
                .port(port)
                .username(username)
                .password(password)
                .connectTimeoutMs(connectTimeoutMs)
//                .keepAliveTimeoutSeconds(keepAliveTimeoutSeconds)
//                .ftpClientConfig(ftpClientConfig)
                .build();
        FtpUtils.ftpHandle(ftpConfig, client -> {

        });

//        FtpUtils.handle(ftpConfig, new FtpCallback() {
//
//            @Override
//            public void process(FTPClient client) {
//
//            }
//        });
    }

    @Test
    @SuppressWarnings("PMD")
    public void ftps() {
        String host = "";
        int port = 21;
        String username = "";
        String password = "";
        int connectTimeoutMs = 5000;
        FtpConfig ftpConfig = FtpConfig.builder(host)
                .port(port)
                .username(username)
                .password(password)
                .connectTimeoutMs(connectTimeoutMs)
                .isFtps(true)
                .build();
        FtpUtils.ftpHandle(ftpConfig, client -> {

        });
    }
    
    @Test
    @SuppressWarnings("PMD")
    public void sftp() {
        String host = "172.28.3.71";
        int port = 22;
        String username = "penglei";
        String password = "penglei";
        int connectTimeoutMs = 5000;
        SftpConfig sftpConfig = SftpConfig.builder(host)
                .port(port)
                .username(username)
                .password(password)
                .connectTimeoutMs(connectTimeoutMs)
                .build();
        FtpUtils.sftpHandle(sftpConfig, client -> {
            try {
                System.out.println(client.ls("/"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
//        FtpUtils.handle(ftpConfig, new FtpCallback() {
//            
//            @Override
//            public void process(FTPClient client) {
//                
//            }
//        });
    }
}
