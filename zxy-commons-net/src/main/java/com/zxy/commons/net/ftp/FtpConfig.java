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

import javax.net.ssl.SSLContext;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPSClient;

/**
 * FtpConfig
 * 
 * <p>
 * <a href="FtpConfig.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("PMD")
public final class FtpConfig {
    private String host;
    private int port;
    private boolean isFtps;
    private String username;
    private String password;
    private int connectTimeoutMs;
    private long keepAliveTimeoutSeconds;
    private FTPClientConfig ftpClientConfig;
    private SSLContext sslContext;
    
    private FtpConfig() {}
    
    /**
     * FtpConfig builder
     * 
     * @param host host
     * @return Builder
    */
    public static Builder builder(String host) {
        return new FtpConfig.Builder(host);
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
        private boolean isFtps = false;
        private int port = isFtps ? FTPSClient.DEFAULT_FTPS_PORT : FTPClient.DEFAULT_PORT;
        private String username = "";
        private String password = "";
        private int connectTimeoutMs = 5000;
        private long keepAliveTimeoutSeconds = 30L;
        private FTPClientConfig ftpClientConfig = new FTPClientConfig();
        private SSLContext sslContext;

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
         * set isFtps
         * @param isFtps isFtps
         * @return Builder
        */
        public Builder isFtps(boolean isFtps) {
            this.isFtps = isFtps;
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
         * set keepAliveTimeoutSeconds
         * @param keepAliveTimeoutSeconds keepAliveTimeoutSeconds
         * @return Builder
        */
        public Builder keepAliveTimeoutSeconds(long keepAliveTimeoutSeconds) {
            this.keepAliveTimeoutSeconds = keepAliveTimeoutSeconds;
            return this;
        }
        
        /**
         * set sslContext
         * @param sslContext sslContext
         * @return Builder
        */
        public Builder sslContext(SSLContext sslContext) {
            this.sslContext = sslContext;
            return this;
        }
        
        /**
         * set ftp client config
         * @param ftpClientConfig ftpClientConfig
         * @return Builder
         */
        public Builder ftpClientConfig(FTPClientConfig ftpClientConfig) {
            this.ftpClientConfig = ftpClientConfig;
            return this;
        }

        /**
         * build FtpConfig
         * @return FtpConfig
        */
        public FtpConfig build() {
            return new FtpConfig(this);
        }

    }

    // 私有化构造方法 外部不能直接new student
    private FtpConfig(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.isFtps = builder.isFtps;
        this.username = builder.username;
        this.password = builder.password;
        this.connectTimeoutMs = builder.connectTimeoutMs;
        this.keepAliveTimeoutSeconds = builder.keepAliveTimeoutSeconds;
        this.ftpClientConfig = builder.ftpClientConfig;
        this.sslContext = builder.sslContext;
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
     * @return the isFtps
     */
    public boolean isFtps() {
        return isFtps;
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
     * @return the keepAliveTimeoutSeconds
     */
    public long getKeepAliveTimeoutSeconds() {
        return keepAliveTimeoutSeconds;
    }

    /**
     * @return the ftpClientConfig
     */
    public FTPClientConfig getFtpClientConfig() {
        return ftpClientConfig;
    }

    /**
     * @return the sslContext
     */
    public SSLContext getSslContext() {
        return sslContext;
    }
}
