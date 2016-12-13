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
package com.zxy.commons.hystrix.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.EnumSet;
import java.util.Properties;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.WebApplicationInitializer;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.netflix.hystrix.contrib.requestservlet.HystrixRequestContextServletFilter;
import com.netflix.hystrix.contrib.requestservlet.HystrixRequestLogViaResponseHeaderServletFilter;

/**
 * HystrixInitializer
 * <p>注意：该服务通过springmvc运行，需要在项目中添加以下依赖：
 * <pre>
 * &lt;dependency&gt;
 *      &lt;groupId&gt;com.zxy&lt;/groupId&gt;
 *      &lt;artifactId&gt;zxy-commons-web&lt;/artifactId&gt;
 *      &lt;version&gt;${zxy_commons_version}&lt;/version&gt;
 * &lt;/dependency&gt;
 * </pre>
 * <p>
 * <a href="HystrixInitializer.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class HystrixInitializer implements WebApplicationInitializer {
    
    private Properties load() {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            final URL url = Resources.getResource("hystrix.properties");
            final ByteSource byteSource = Resources.asByteSource(url);
            
            inputStream = byteSource.openBufferedStream();
            properties.load(inputStream);
            // properties.list(System.out);
        } catch (Exception e) {
            // do nothing
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
     * {@inheritDoc}
     */
    public void onStartup(ServletContext container) throws ServletException {
//        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(container);
        Properties properties = load();
        String enabled = properties.getProperty("hystrix.stream.enabled");
        if (StringUtils.isBlank(enabled) || "true".equalsIgnoreCase(enabled)) {

            // AnnotationConfigWebApplicationContext ctx = new
            // AnnotationConfigWebApplicationContext();
            // ctx.register(AppConfig.class);
            // ctx.setServletContext(container);

            // ServletRegistration.Dynamic servlet =
            // container.addServlet("dispatcher", new DispatcherServlet(ctx));
            // WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(container);
            // servlet.setLoadOnStartup(1);
            // servlet.addMapping("/");

            FilterRegistration.Dynamic hystrixRequestContextServletFilter = container
                    .addFilter("HystrixRequestContextServletFilter", new HystrixRequestContextServletFilter());
            hystrixRequestContextServletFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

            FilterRegistration.Dynamic hystrixRequestLogViaResponseHeaderServletFilter = container.addFilter(
                    "HystrixRequestLogViaResponseHeaderServletFilter",
                    new HystrixRequestLogViaResponseHeaderServletFilter());
            hystrixRequestLogViaResponseHeaderServletFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),
                    true, "/*");

            ServletRegistration.Dynamic hystrixMetricsStreamServlet = container
                    .addServlet("HystrixMetricsStreamServlet", new HystrixMetricsStreamServlet());
            hystrixMetricsStreamServlet.addMapping("/hystrix.stream");

            // ServletRegistration.Dynamic proxyStreamServlet =
            // container.addServlet("ProxyStreamServlet", new
            // ProxyStreamServlet());
            // proxyStreamServlet.addMapping("/proxy.stream");
        }
    }

}