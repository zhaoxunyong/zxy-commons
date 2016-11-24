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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.google.common.base.Strings;

/**
 * Mybatis中将string转换为long类型
 * 
 * <p>
 * <a href="String2LongTypeHandler.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class String2LongTypeHandler implements TypeHandler<String> {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet,
     * java.lang.String)
     */
    @Override
    public String getResult(ResultSet arg0, String arg1) throws SQLException {
        long value = arg0.getLong(arg1);
        return String.valueOf(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.ibatis.type.TypeHandler#getResult(java.sql.CallableStatement,
     * int)
     */
    @Override
    public String getResult(CallableStatement arg0, int arg1) throws SQLException {
        long value = arg0.getLong(arg1);
        return String.valueOf(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.ibatis.type.TypeHandler#setParameter(java.sql.
     * PreparedStatement, int, java.lang.Object,
     * org.apache.ibatis.type.JdbcType)
     */
    @Override
    public void setParameter(PreparedStatement arg0, int arg1, String arg2, JdbcType arg3) throws SQLException {
        if (!Strings.isNullOrEmpty(arg2)) {
            long value = Long.parseLong((String) arg2);
            arg0.setLong(arg1, value);
        } else {
            arg0.setNull(arg1, Types.BIGINT);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet,
     * int)
     */
    @Override
    public String getResult(ResultSet arg0, int arg1) throws SQLException {
        long value = arg0.getLong(arg1);
        return String.valueOf(value);
    }
}
