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
package com.zxy.commons.mybatis;

/**
 * 功能描述
 * 
 * <p>
 * <a href="HandleDataSource.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class HandleDataSource {
    public static final String MASTER = "MASTER";
    public static final String SLAVE = "SLAVE";

    private static final ThreadLocal<String> HOLDER = new ThreadLocal<String>();

    private HandleDataSource() {
    }

    /**
     * 保存对应数据库名到本地本地线程对象
     * 
     * @param datasource datasource
     */
    public static void putDataSource(String datasource) {
        HOLDER.set(datasource);
    }

    /**
     * 获取选取到的数据源
     * 
     * @return datasource
     */
    public static String getDataSource() {
        return HOLDER.get();
    }

    /**
     * 清除本地线程映射的数据源
     */
    public static void clearCustomerType() {
        HOLDER.remove();
    }
}
