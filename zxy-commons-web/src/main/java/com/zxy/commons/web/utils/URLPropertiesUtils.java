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
package com.zxy.commons.web.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

import com.google.common.io.Resources;
import com.zxy.commons.lang.conf.AbstractConfigProperties;

/**
 * 获取url数据
 * 
 * <p>
 * <a href="URLPropertiesUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class URLPropertiesUtils extends AbstractConfigProperties {

    private final static class URLPropertiesUtilsBuilder {
        private final static URLPropertiesUtils INSTANCE = new URLPropertiesUtils();
    }

    /**
     * 得到单例的URLPropertiesUtils实例
     * 
     * @return 单例的URLPropertiesUtils实例
     */
    public static URLPropertiesUtils getInstance() {
        return URLPropertiesUtilsBuilder.INSTANCE;
    }
    
    private String getWebInfPath() {
        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
        String path = url.toString();
        int index = path.indexOf("WEB-INF");

        if (index == -1) {
            index = path.indexOf("classes");
        }

        if (index == -1) {
            index = path.indexOf("bin");
        }
        
        if(index != -1) {
            path = path.substring(0, index);

            if (path.startsWith("zip")) {// 当class文件在war中时，此时返回zip:D:/...这样的路径
                path = path.substring(4);
            } else if (path.startsWith("file")) {// 当class文件在class文件中时，此时返回file:/D:/...这样的路径
                path = path.substring(6);
            } else if (path.startsWith("jar")) {// 当class文件在jar文件里面时，此时返回jar:file:/D:/...这样的路径
                path = path.substring(10);
            }
            return path.replace("/target", "");
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected URL getPath() throws IOException {
        String path = getWebInfPath();
        if(StringUtils.isNotBlank(path)) {
            String webInf = "/"+ path;
            String urlPath = "WEB-INF/view/config/url.properties";
            if(new File(webInf+"src/main/webapp/"+urlPath).exists()) {
                return new File(webInf+"src/main/webapp/"+urlPath).toURL();
            } else if(new File(webInf+"/"+urlPath).exists()) {
                return new File(webInf+"/"+urlPath).toURL();
            } else {
                return Resources.getResource("url.properties");
            }
        }
        return Resources.getResource("url.properties");
    }
}
