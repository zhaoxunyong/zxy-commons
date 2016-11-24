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
package com.zxy.commons.lang.conf;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;

/** 
 * 读取properties配置文件的基类
 * 
 * <p>
 * 如果需要读取配置文件时，需要继承此类
 * <p>
 * <a href="BaseConfigProperties.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
 */
public abstract class AbstractConfigProperties {
    
	private Properties properties;
	
	protected AbstractConfigProperties() {
		try {
			properties = load();
		} catch (IOException e) {
		    throw new AssertionError(e);
		}
	}
    
    /**
     * 加载配置文件为Properties
     * 
     * @return Properties
     * @throws IOException
     */
	@SuppressWarnings("PMD.EmptyCatchBlock")
    private Properties load() throws IOException {
//		final URL url = Resources.getResource(resource);
		final URL url = getPath();
		final ByteSource byteSource = Resources.asByteSource(url);
		final Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = byteSource.openBufferedStream();
			properties.load(inputStream);
//			properties.list(System.out);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioException) {
					// do nothing
				}
			}
		}
		return properties;
	}

    /**
     * 获取配置文件的路径
     * 
     * <p>
     * 注意：
     * 子类必实现该方法，比如说需要读取配置config.properties：
     * <pre>
     *   protected URL getPath() {
     *      return this.getClass().getResource("/config.properties");
     *   }
     * </pre>
     * @return 配置文件的路径
     * @throws IOException IOException
    */
    protected abstract URL getPath() throws IOException;
    
    protected URL classpath2URL(final String name) throws MalformedURLException {
        String configName = name;
        if(configName.charAt(0) == '/') {
            configName = name.replaceAll("^/", "");
        }
        return this.getClass().getResource("/"+configName);
    }

    /**
     * 读取一个key的String值
     * 
     * @param key key
     * @return value
     */
    public String getString(String key) {
        return properties.getProperty(key);
    }

    /**
     * 读取一个key的boolean值：true/false
     * 
     * @param key key
     * @return value
     */
    public boolean getBoolean(String key) {
        String value = properties.getProperty(key);
        return BooleanUtils.toBoolean(value);
    }

    /**
     * 读取一个key的int值
     * 
     * @param key key
     * @return value
     */
    public int getInt(String key) {
        String value = properties.getProperty(key);
        return NumberUtils.toInt(value, 0);
    }
    /**
     * 读取一个key的byte值
     * 
     * @param key key
     * @return value
     */
    public byte getByte(String key) {
        String value = properties.getProperty(key);
        return NumberUtils.toByte(value);
    }

    /**
     * 读取一个key的int值
     * 
     * @param key key
     * @return value
     */
    public long getLong(String key) {
        String value = properties.getProperty(key);
        return NumberUtils.toLong(value, 0);
    }
}
