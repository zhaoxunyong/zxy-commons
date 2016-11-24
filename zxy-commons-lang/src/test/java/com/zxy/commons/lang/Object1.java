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
package com.zxy.commons.lang;

import com.zxy.commons.lang.utils.ObjectsUtils;

/**
 * Object1
 * 
 * <p>
 * <a href="Object1.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class Object1 {
    private int id;
    private String username;
    private String password;
    
    public Object1(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public Object1(int id, String username, String password) {
        this(username, password);
        this.id = id;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }



    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }



    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return ObjectsUtils.toString(this);
    }
}
