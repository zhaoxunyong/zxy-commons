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
package com.zxy.commons.pool;

/** 
 * Pool executer
 * 
 * <p>
 * <a href="PoolExecuter.java"><i>View Source</i></a>
 * 
 * @param <P> Pool object
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public interface PoolExecuter<P> {
    
    /**
     * execute
     * 
     * @param <T> return type
     * @param execution execution
     * @return T
     */
    public <T> T execute(Execution<P, T> execution);
    
    /** 
     * Execution
     * 
     * <p>
     * <a href="Execution.java"><i>View Source</i></a>
     * 
     * @param <P> Pool object
     * @param <T> return type
     * @author zhaoxunyong@qq.com
     * @version 1.0
     * @since 1.0 
    */
    public interface Execution<P, T> {

        /**
         * Execute code block
         * 
         * @param poolObj Pool object
         * @return T
         */
        public T execute(P poolObj);
    }
}
