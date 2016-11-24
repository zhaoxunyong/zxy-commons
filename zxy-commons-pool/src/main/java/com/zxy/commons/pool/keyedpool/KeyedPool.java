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
package com.zxy.commons.pool.keyedpool;


import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxy.commons.pool.exception.PoolException;



/** 
 * KV pool公共类
 * 
 * <p>
 * <a href="KeyedPool.java"><i>View Source</i></a>
 * 
 * @param <K> key
 * @param <V> Value
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
 */
public abstract class KeyedPool<K,V> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private static final byte WHENEXHAUSTEDACTION  = TransportPoolConfigurator.conf.whenExhaustedAction;
    private final GenericKeyedObjectPool<K,V> internalPool;

    /**
     * 构造函数
     * 
     * @param factory factory
     * @param config config
     */
    public KeyedPool(KeyedPooledObjectFactory<K,V> factory, GenericKeyedObjectPoolConfig config) {
        this.internalPool = new GenericKeyedObjectPool<>(factory, config);
    }
    
    protected abstract void closeResource(V resource);
    
    
    /**
     * getInternalPool
     * 
     * @return GenericObjectPool
     */
//    public GenericObjectPool<T> getInternalPool() {
//        return internalPool;
//    }


    /**
     * 从池中获取对象
     * 
     * @param key key
     */
    public void addObject(K key) {
        try {
            this.internalPool.addObject(key);
        } catch (Exception e) {
            throw new PoolException("Could not add resource to the pool", e);
        }
    }

    /**
     * 从池中获取对象
     * 
     * @param key key
     * @return 对象
     */
    public V getResource(K key) {
        try {
            return this.internalPool.borrowObject(key);
        } catch (Exception e) {
            throw new PoolException("Could not get a resource from the pool", e);
        }
        /*switch(WHENEXHAUSTEDACTION){
        case 0:
        default:
            try {
                return this.internalPool.borrowObject(k);
            } catch (Exception e) {
                throw new PoolException("Could not get a resource from the pool", e);
            }
//            break;
        case 1:
            try {
                return this.internalPool.borrowObject(k);
            } catch (NoSuchElementException e) {
                logger.warn(e.getMessage());
                try {
                    return internalPool.getFactory().makeObject(k).getObject();
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
     * @param key key
     * @param value value
     */
    public void returnResource(K key, V value) {
        try {
            this.internalPool.returnObject(key, value);
        } catch (Exception e) {
            throw new PoolException("Could not return the resource to the pool", e);
        }
        /*switch(WHENEXHAUSTEDACTION){
        case 0:
        default:
            try {
                this.internalPool.returnObject(k, v);
            } catch (Exception e) {
                throw new PoolException("Could not return the resource to the pool", e);
            }
            break;
        case 1:
            try {
                this.internalPool.returnObject(k, v);
            } catch (IllegalStateException e) {
                logger.warn(e.getMessage());
                closeResource(v);
            } catch (Exception e) {
                throw new PoolException("Could not return the resource to the pool(GROW)", e);
            }
            break;
        }*/
        
    }

    /**
     * 将对象放回到池中
     * 
     * @param key key
     * @param value value
     */
    public void returnBrokenResource(K key, V value) {
        try {
            this.internalPool.invalidateObject(key, value);
        } catch (Exception e) {
            throw new PoolException("Could not return the broken resource to the pool", e);
        }
        /*switch(WHENEXHAUSTEDACTION){
        case 0:
        default:
            try {
                this.internalPool.invalidateObject(k, v);
            } catch (Exception e) {
                throw new PoolException("Could not return the broken resource to the pool", e);
            }
            break;
        case 1:
            try {
                this.internalPool.invalidateObject(k, v);
            } catch (IllegalStateException e) {
                logger.warn(e.getMessage());
                closeResource(v);
            } catch (Exception e) {
                throw new PoolException("Could not return the broken resource to the pool(GROW)", e);
            }
            break;
        }*/
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
