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
package com.zxy.commons.hystrix;

import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.netflix.hystrix.contrib.requestservlet.HystrixRequestContextServletFilter;
import com.netflix.hystrix.contrib.requestservlet.HystrixRequestLogViaResponseHeaderServletFilter;
import com.zxy.commons.jetty.server.AbstractWebServer;

/** 
 * HystrixServer
 * 
 * <p>
 * <a href="HystrixServer.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
*/
public class HystrixServer extends AbstractWebServer {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getContextPath() {
        return "/";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, HttpServlet> getServlets() {
        Map<String, HttpServlet> servlets = Maps.newHashMap();
        servlets.put("/hystrix.stream", new HystrixMetricsStreamServlet());
        return servlets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, Filter> getFilters() {
        Map<String, Filter> filters = Maps.newHashMap();
        filters.put("/*", new HystrixRequestContextServletFilter());
        filters.put("/*", new HystrixRequestLogViaResponseHeaderServletFilter());
        return filters;
    }

}
