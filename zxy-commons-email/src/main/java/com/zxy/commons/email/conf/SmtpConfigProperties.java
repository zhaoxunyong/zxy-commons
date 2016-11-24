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
package com.zxy.commons.email.conf;

import java.io.IOException;
import java.net.URL;

import com.zxy.commons.lang.conf.AbstractConfigProperties;

/**
 * SMTP配置文件
 * 
 * <p>
 * <a href="SmtpConfigProperties.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class SmtpConfigProperties extends AbstractConfigProperties {

    private final static class SmtpConfigPropertiesBuilder {
        private final static SmtpConfigProperties INSTANCE = new SmtpConfigProperties();
    }

    /**
     * 得到单例的SmtpConfigProperties实例
     * 
     * @return 单例的SmtpConfigProperties实例
     */
    public static SmtpConfigProperties getInstance() {
        return SmtpConfigPropertiesBuilder.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zxy.commons.lang.conf.AbstractConfigurator#getPath()
     */
    @Override
    protected URL getPath() throws IOException {
        return classpath2URL("smtp.properties");
    }

    public String getSmtpHost() {
        return getString("smtp_host");
    }

    public int getSmtpPort() {
        return getInt("smtp_port");
    }

    public String getSmtpUserName() {
        return getString("smtp_username");
    }

    public String getSmtpPassword() {
        return getString("smtp_password");
    }

    public String getSmtpFrom() {
        return getString("smtp_from");
    }
}
