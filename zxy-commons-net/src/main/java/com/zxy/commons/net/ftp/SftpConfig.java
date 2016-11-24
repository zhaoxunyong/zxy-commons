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

/**
 * SftpConfig
 * 
 * <p>
 * <a href="SftpConfig.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("PMD")
public final class SftpConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private int connectTimeoutMs;
    private int intervalMs;
    
    private SftpConfig() {}
    
    /**
     * FtpConfig builder
     * 
     * @param host host
     * @return Builder
    */
    public static Builder builder(String host) {
        return new SftpConfig.Builder(host);
    }

    /** 
     * FtpConfig Builder
     * 
     * <p>
     * <a href="Builder.java"><i>View Source</i></a>
     * 
     * @author zhaoxunyong@qq.com
     * @version 1.0
     * @since 1.0 
    */
    public static class Builder {
        // requried
        private String host;
        // optional
        private int port = 22;
        private String username = "";
        private String password = "";
        private int connectTimeoutMs = 5000;
        private  int intervalMs = 0;

        // builder构造方法 必须设置限定属性的值
        public Builder(String host) {
            this.host = host;
        }

        // 外部提供的设置可选属性的值
        /**
         * set port
         * @param port port
         * @return Builder
        */
        public Builder port(int port) {
            this.port = port;
            return this;
        }
        
        /**
         * set username
         * @param username username
         * @return Builder
        */
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        
        /**
         * set password
         * @param password password
         * @return Builder
        */
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        
        /**
         * set connectTimeoutMs
         * @param connectTimeoutMs connectTimeoutMs
         * @return Builder
        */
        public Builder connectTimeoutMs(int connectTimeoutMs) {
            this.connectTimeoutMs = connectTimeoutMs;
            return this;
        }
        
        /**
         * set intervalMs
         * @param intervalMs intervalMs
         * @return Builder
        */
        public Builder intervalMs(int intervalMs) {
            this.intervalMs = intervalMs;
            return this;
        }
        
        /**
         * build FtpConfig
         * @return FtpConfig
        */
        public SftpConfig build() {
            return new SftpConfig(this);
        }

    }

    // 私有化构造方法 外部不能直接new student
    private SftpConfig(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
        this.connectTimeoutMs = builder.connectTimeoutMs;
        this.intervalMs = builder.intervalMs;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the connectTimeoutMs
     */
    public int getConnectTimeoutMs() {
        return connectTimeoutMs;
    }


    /**
     * @return the intervalMs
     */
    public int getIntervalMs() {
        return intervalMs;
    }
}
