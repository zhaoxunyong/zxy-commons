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

import com.zxy.commons.pool.exception.PoolException;

/** 
 * Abstract pool executer
 * 
 * <p>
 * <a href="AbstractPoolExecuter.java"><i>View Source</i></a>
 * 
 * @param <P> Pool object
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public abstract class AbstractPoolExecuter<P> implements PoolExecuter<P> {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T execute(Execution<P, T> execution) {
        P poolObj = null;
        try {
            poolObj = this.get();
            return execution.execute(poolObj);
        } catch(PoolException e) {
            if(poolObj != null) {
                closeBroken(poolObj);
            }
            throw e;
        } finally {
            if(poolObj != null) {
                close(poolObj);
            }
        }
    }
    
    /**
     * get
     */
    protected abstract P get();

    /**
     * close
     */
    protected abstract void close(P poolObj);
    
    /**
     * close broken
     * 
     * @param poolObj Pool object
     */
    public abstract void closeBroken(P poolObj);

}
