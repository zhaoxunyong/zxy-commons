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

/** 
 * Abstract object pool factory
 * 
 * <p>
 * 注意：必须确保类为单例模式
 * <p>
 * <a href="AbstractObjectPoolFactory.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
class TestObjectPoolFactory {
//  private static Logger logger = LoggerFactory.getLogger(FastdfsPoolFactory.class);
    private final ObjectPool<PoolObj> pool;
    
    private final static class TestObjectPoolFactoryBuilder {
        private final static TestObjectPoolFactory BUILDER = new TestObjectPoolFactory();
    }
    
    public TestObjectPoolFactory() {
        this.pool = new TestPool();
    }
    
    public static TestObjectPoolFactory getInstance() {
        return TestObjectPoolFactoryBuilder.BUILDER;
    }
    
    /**
     * 从池中获取pool对象
     * 
     * @return Pool object
     */
    public PoolObj get() {
        return pool.getResource();
    }
  
    /**
     * 将pool对象放回到池中
     * 
     * @param resource pool对象
     */
    public void close(PoolObj resource) {
        if (resource != null) {
            pool.returnResource(resource);
        }
    }
  
    /**
     * 将pool对象放回到池中
     * 
     * @param resource pool对象
     */
    public void closeBroken(PoolObj resource) {
        if (resource != null) {
            pool.returnBrokenResource(resource);
        }
    }
}
