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

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import com.zxy.commons.jetty.exception.ServerErrorException;

import java.net.InetSocketAddress;

/**
 * Abstract jetty server
 * <p>
 * <a href="AbstractJettyServer.java"><i>View Source</i></a>
 *
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@Deprecated
public abstract class AbstractJettyServer {

    protected abstract String getDescriptor();

    protected abstract String getContextPath();

    protected abstract String getResourceBase();

    protected Server startServer(String host, int port) {
        InetSocketAddress address = null;
        if (host == null || "".equals(host)) {
            address = new InetSocketAddress(port);
        } else {
            address = new InetSocketAddress(host, port);
        }
        Server server = new Server(address);
        WebAppContext context = new WebAppContext();
//        context.setContextPath("/");
        context.setContextPath(getContextPath());
//        context.setDescriptor(this.getClass().getResource("/web.xml").getFile());
        context.setDescriptor(getDescriptor());
//        context.setResourceBase("/");
        context.setResourceBase(getResourceBase());
        context.setParentLoaderPriority(true);
        server.setHandler(context);
        try {
            server.start();
        } catch (Exception e) {
            throw new ServerErrorException(e);
        }
//         server.join();
        return server;

    }
}
