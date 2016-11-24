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
package com.zxy.commons.pool.objectpool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxy.commons.pool.exception.PoolException;


/** 
 * pool公共类
 * 
 * <p>
 * <a href="ObjectPool.java"><i>View Source</i></a>
 * 
 * @param <T> Pool object
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class ObjectPool<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final GenericObjectPool<T> internalPool;
//    private final GenericObjectPoolConfig config;

    /**
     * 构造函数
     * 
     * @param poolConfig poolConfig
     * @param factory factory
     */
    public ObjectPool(PooledObjectFactory<T> factory, GenericObjectPoolConfig config) {
        this.internalPool = new GenericObjectPool<T>(factory, config);
//        this.config = config;
    }
    
//    protected abstract void closeResource(T resource);
    
    
    /**
     * 从池中获取对象
     */
    public void addObject() {
        try {
            this.internalPool.addObject();
        } catch (Exception e) {
            throw new PoolException("Could not add resource to the pool", e);
        }
    }

    /**
     * 从池中获取对象
     * 
     * @return 对象
     */
    public T getResource() {
        try {
            return this.internalPool.borrowObject();
        } catch (Exception e) {
            throw new PoolException("Could not get a resource from the pool", e);
        }
        /*switch(config.getWhenExhaustedAction()){
        case 0:
        default:
            try {
                return this.internalPool.borrowObject();
            } catch (Exception e) {
                throw new PoolException("Could not get a resource from the pool", e);
            }
//            break;
        case 1:
            try {
                return this.internalPool.borrowObject();
            } catch (NoSuchElementException e) {
                logger.warn(e.getMessage());
                try {
                    return internalPool.getFactory().makeObject().getObject();
                } catch (Exception e1) {
                    throw new PoolException("Could not get a resource from the pool(GROW)", e1);
                }
            } catch (Exception e) {
                throw new PoolException("Could not get a resource from the pool(GROW)", e);
            }
//            break;
        }*/
    }

    /**
     * 将对象放回到池中
     * 
     * @param resource 对象
     */
    public void returnResource(T resource) {
        try {
            this.internalPool.returnObject(resource);
        } catch (Exception e) {
            throw new PoolException("Could not return the resource to the pool", e);
        }
        /*switch(config.getWhenExhaustedAction()){
        case 0:
        default:
            try {
                this.internalPool.returnObject(resource);
            } catch (Exception e) {
                throw new PoolException("Could not return the resource to the pool", e);
            }
            break;
        case 1:
            try {
                this.internalPool.returnObject(resource);
            } catch (IllegalStateException e) {
                logger.warn(e.getMessage());
                closeResource(resource);
            } catch (Exception e) {
                throw new PoolException("Could not return the resource to the pool(GROW)", e);
            }
            break;
        }*/
    }

    /**
     * 将对象放回到池中
     * 
     * @param resource 对象
     */
    public void returnBrokenResource(T resource) {
        try {
            this.internalPool.invalidateObject(resource);
        } catch (Exception e) {
            throw new PoolException("Could not return the broken resource to the pool", e);
        }
    }

    /**
     * 销毁对象
    */
    public void destroy() {
        try {
            this.internalPool.close();
        } catch (Exception e) {
            throw new PoolException("Could not destroy the pool", e);
        }
    }
}
