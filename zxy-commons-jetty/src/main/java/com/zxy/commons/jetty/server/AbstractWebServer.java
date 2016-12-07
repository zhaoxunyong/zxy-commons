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
package com.zxy.commons.jetty.server;

import com.zxy.commons.jetty.exception.ServerErrorException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.net.InetSocketAddress;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Abstract jetty server
 *
 * <p>
 * <a href="AbstractWebServer.java"><i>View Source</i></a>
 *
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractWebServer {

    protected abstract String getContextPath();

    protected abstract Map<String, HttpServlet> getServlets();

    protected Map<String, Filter> getFilters() {
        return new HashMap<>();
    }
    
    protected Set<EventListener> getEventListeners() {
        return new HashSet<>();
    }

    /**
     * startServer
     * @param port port
     * @return Server
    */
    public Server startServer(int port) {
        return startServer(null, port);
    }

    /**
     * startServer
     * @param host host
     * @param port port
     * @return Server
    */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public Server startServer(String host, int port) {
        InetSocketAddress address = null;
        if (host == null || "".equals(host)) {
            address = new InetSocketAddress(port);
        } else {
            address = new InetSocketAddress(host, port);
        }
        Server server = new Server(address);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(getContextPath());
        server.setHandler(context);

//        handler.addServletWithMapping(HelloServlet.class, "/*");
//        handler.addFilterWithMapping(HelloPrintingFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        
        Map<String, HttpServlet> servlets = getServlets();
        if(servlets != null && !servlets.isEmpty()) {
            for(Map.Entry<String, HttpServlet> entry : servlets.entrySet()) {
                context.addServlet(new ServletHolder(entry.getValue()), entry.getKey());
            }
        }
        
        Map<String, Filter> filters = getFilters();
        if(filters != null && !filters.isEmpty()) {
            for(Map.Entry<String, Filter> entry : filters.entrySet()) {
                context.addFilter(new FilterHolder(entry.getValue()), entry.getKey(), EnumSet.of(DispatcherType.REQUEST));
            }
        }
        
        Set<EventListener> eventListeners = getEventListeners();
        if(eventListeners != null && !eventListeners.isEmpty()) {
            for(EventListener eventListener : eventListeners) {
                context.addEventListener(eventListener);
            }
        }
        
        try {
            server.start();
        } catch (Exception e) {
            throw new ServerErrorException(e);
        }
        //  server.join();
        return server;
    }
}