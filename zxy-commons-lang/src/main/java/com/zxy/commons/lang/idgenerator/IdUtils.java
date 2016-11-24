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
package com.zxy.commons.lang.idgenerator;

import com.google.common.hash.Hashing;

/**
 * id生成工具
 * 
 * <p>
 * <a href="IdUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class IdUtils {
    private final static IdWorker IDWORKER = IdWorker.getInstance();
    public final static String SPLIT = "_";
    private static final int ID_LENGTH = 16;
    
    private IdUtils(){}

//    public static int random() {
//        return new Random().nextInt(MAX - MIN + 1) + MIN;
//    }

    /**
     * 生成long型id
     * @return long型id
    */
    public static long genLongId() {
        return IDWORKER.getId();
    }

    /**
     * 生成字符串id
     * <p>
     * 生成规则为：uuid取md5后再截取16位
     * 
     * @return 字符串id
     */
    public static String genStringId() {
        String uuid = UUIDUtils.newUUID();
        String md5 = Hashing.md5().hashBytes(uuid.getBytes()).toString().substring(8, 24);
        if (md5.length() > ID_LENGTH) {
            md5 = md5.substring(0, ID_LENGTH);
        }
        return md5;
    }
}
