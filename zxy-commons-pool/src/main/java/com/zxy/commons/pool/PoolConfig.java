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
package com.zxy.commons.pool;

/** 
 * pool配置
 * 
 * <p>
 * <a href="PoolConfig.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
 */
public class PoolConfig {
    private int maxTotalPerKey;
    private int minIdlePerKey;
    private int maxIdle;
    private int minIdle;
    private int maxTotal;
    private int maxWait;
    private byte whenExhaustedAction;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private String serverIp;
    private int serverPort;
    private Integer clientConnectTimeout;
    private Integer clientReceiveTimeout;
    private Integer clientSendTimeout;
    
    /**
     * @return the maxTotalPerKey
     */
    public int getMaxTotalPerKey() {
        return maxTotalPerKey;
    }
    /**
     * @param maxTotalPerKey the maxTotalPerKey to set
     */
    public void setMaxTotalPerKey(int maxTotalPerKey) {
        this.maxTotalPerKey = maxTotalPerKey;
    }
    /**
     * @return the minIdlePerKey
     */
    public int getMinIdlePerKey() {
        return minIdlePerKey;
    }
    /**
     * @param minIdlePerKey the minIdlePerKey to set
     */
    public void setMinIdlePerKey(int minIdlePerKey) {
        this.minIdlePerKey = minIdlePerKey;
    }
    /**
     * @return the maxIdle
     */
    public int getMaxIdle() {
        return maxIdle;
    }
    /**
     * @param maxIdle the maxIdle to set
     */
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }
    /**
     * @return the minIdle
     */
    public int getMinIdle() {
        return minIdle;
    }
    /**
     * @param minIdle the minIdle to set
     */
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }
    /**
     * @return the maxTotal
     */
    public int getMaxTotal() {
        return maxTotal;
    }
    /**
     * @param maxTotal the maxTotal to set
     */
    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }
    /**
     * @return the maxWait
     */
    public int getMaxWait() {
        return maxWait;
    }
    /**
     * @param maxWait the maxWait to set
     */
    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }
    /**
     * @return the whenExhaustedAction
     */
    public byte getWhenExhaustedAction() {
        return whenExhaustedAction;
    }
    /**
     * @param whenExhaustedAction the whenExhaustedAction to set
     */
    public void setWhenExhaustedAction(byte whenExhaustedAction) {
        this.whenExhaustedAction = whenExhaustedAction;
    }
    /**
     * @return the timeBetweenEvictionRunsMillis
     */
    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }
    /**
     * @param timeBetweenEvictionRunsMillis the timeBetweenEvictionRunsMillis to set
     */
    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }
    /**
     * @return the minEvictableIdleTimeMillis
     */
    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }
    /**
     * @param minEvictableIdleTimeMillis the minEvictableIdleTimeMillis to set
     */
    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }
    /**
     * @return the testWhileIdle
     */
    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }
    /**
     * @param testWhileIdle the testWhileIdle to set
     */
    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }
    /**
     * @return the testOnBorrow
     */
    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }
    /**
     * @param testOnBorrow the testOnBorrow to set
     */
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }
    /**
     * @return the serverIp
     */
    public String getServerIp() {
        return serverIp;
    }
    /**
     * @param serverIp the serverIp to set
     */
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
    /**
     * @return the serverPort
     */
    public int getServerPort() {
        return serverPort;
    }
    /**
     * @param serverPort the serverPort to set
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
    /**
     * @return the clientConnectTimeout
     */
    public Integer getClientConnectTimeout() {
        return clientConnectTimeout;
    }
    /**
     * @param clientConnectTimeout the clientConnectTimeout to set
     */
    public void setClientConnectTimeout(Integer clientConnectTimeout) {
        this.clientConnectTimeout = clientConnectTimeout;
    }
    /**
     * @return the clientReceiveTimeout
     */
    public Integer getClientReceiveTimeout() {
        return clientReceiveTimeout;
    }
    /**
     * @param clientReceiveTimeout the clientReceiveTimeout to set
     */
    public void setClientReceiveTimeout(Integer clientReceiveTimeout) {
        this.clientReceiveTimeout = clientReceiveTimeout;
    }
    /**
     * @return the clientSendTimeout
     */
    public Integer getClientSendTimeout() {
        return clientSendTimeout;
    }
    /**
     * @param clientSendTimeout the clientSendTimeout to set
     */
    public void setClientSendTimeout(Integer clientSendTimeout) {
        this.clientSendTimeout = clientSendTimeout;
    }
}
