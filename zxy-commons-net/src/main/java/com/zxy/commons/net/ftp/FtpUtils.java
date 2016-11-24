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

import java.io.IOException;

import com.zxy.commons.net.exception.NetException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * FtpUtils
 * 
 * <p>
 * <a href="FtpUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class FtpUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(FtpUtils.class);
    
    private FtpUtils() {}
    
    /**
     * FTP handle
     * 
     * @param ftpConfig ftp config
     * @param callback ftp callback
    */
    public static void ftpHandle(FtpConfig ftpConfig, FtpCallback callback) {
        FTPClient client = null;
        if(ftpConfig.isFtps() && ftpConfig.getSslContext() != null) {
            client = new FTPSClient(ftpConfig.getSslContext());
        } else if(ftpConfig.isFtps()){
            client = new FTPSClient();
        } else {
            client = new FTPClient();
        }
            
        client.configure(ftpConfig.getFtpClientConfig());
        try {
//            client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            client.connect(ftpConfig.getHost(), ftpConfig.getPort());
            client.setConnectTimeout(ftpConfig.getConnectTimeoutMs());
            client.setControlKeepAliveTimeout(ftpConfig.getKeepAliveTimeoutSeconds());
            if(!Strings.isNullOrEmpty(ftpConfig.getUsername())) {
                client.login(ftpConfig.getUsername(), ftpConfig.getPassword());
            }
            LOGGER.trace("Connected to {}, reply: {}", ftpConfig.getHost(), client.getReplyString());

            // After connection attempt, you should check the reply code to verify success.
            int reply = client.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                throw new NetException("FTP server refused connection.");
            }
            callback.process(client);
        } catch(Exception e) {
            throw new NetException(e);
        } finally {
            if (client.isConnected()) {
                try {
                    client.logout();
                } catch (IOException ioe) {
                    LOGGER.warn(ioe.getMessage());
                }
                try {
                    client.disconnect();
                } catch (IOException ioe) {
                    LOGGER.warn(ioe.getMessage());
                }
            }
        }
    }
    

    
    /**
     * FTP handle
     * 
     * @param ftpConfig ftp config
     * @param callback ftp callback
    */
    public static void sftpHandle(SftpConfig ftpConfig, SftpCallback callback) {
        Session session = null;
        ChannelSftp client = null;
        try {
            JSch jsch = new JSch();
            String host = ftpConfig.getHost();
            int port = ftpConfig.getPort();
            String username = ftpConfig.getUsername();
            String password = ftpConfig.getPassword();
            int intervalMs = ftpConfig.getIntervalMs();
            session = jsch.getSession(username, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.setServerAliveInterval(intervalMs);
            session.connect(ftpConfig.getConnectTimeoutMs());
            Channel channel = session.openChannel("sftp");
            channel.connect(ftpConfig.getConnectTimeoutMs());
            client = (ChannelSftp) channel;
            if (!client.isConnected()) {
                throw new NetException("FTP server refused connection.");
            }
            LOGGER.trace("Connected to host: {}, port: {}.", host, port);
            callback.process(client);
        } catch (JSchException e) {
            throw new NetException(e);
        } finally {
            if (client != null) {
                client.exit();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        
        
//        Session session = null;
//        ChannelSftp client = null;
//        try {
//            String host = ftpConfig.getHost();
//            int port = ftpConfig.getPort();
//            String username = ftpConfig.getUsername();
//            String password = ftpConfig.getPassword();
//            FileSystemOptions fso = new FileSystemOptions();
//            SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fso, "no");
//            session = SftpClientFactory.createConnection(host, port, username.toCharArray(), password.toCharArray(), fso);
//            Channel channel = session.openChannel("sftp");
//            channel.connect();
//            client = (ChannelSftp) channel;
//            
//            if(!client.isConnected()) {
//                throw new FtpException("FTP server refused connection.");
//            }
//            callback.process(client);
//        } catch (FileSystemException | JSchException e) {
//            throw new FtpException(e);
//        } finally {
//            if (client != null) {
//                client.exit();
//            }
//            if (session != null) {
//                session.disconnect();
//            }
//        }
    }
}
