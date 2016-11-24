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
package com.zxy.commons.pool.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zxy.commons.pool.PoolExecuter.Execution;
import com.zxy.commons.pool.objectpool.PoolObj;
import com.zxy.commons.pool.objectpool.TestExecuter;

/** 
 * Pool test
 * 
 * <p>
 * <a href="PoolTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class PoolTest {
//    private final TestObjectPoolFactory factory = TestObjectPoolFactory.getInstance();
    private final TestExecuter executer = TestExecuter.getInstance();
    
//    @Test
//    @SuppressWarnings("PMD")
//    public void factoryTest() {
//        PoolObj poolObj1 = factory.get();
//        System.out.println("poolObj1==="+poolObj1);
//        
//        PoolObj poolObj2 = factory.get();
//        System.out.println("poolObj2==="+poolObj2);
//        
//        assertNotEquals(poolObj1, poolObj2);
//        
//        // close all pool
//        factory.close(poolObj1);
//        factory.close(poolObj2);
//        
//        
//        PoolObj poolObj3 = factory.get();
//        System.out.println("poolObj3==="+poolObj3);
//        assertEquals(poolObj3, poolObj2);
//        
//        PoolObj poolObj4 = factory.get();
//        System.out.println("poolObj4==="+poolObj4);
//        assertEquals(poolObj4, poolObj1);
//        
//        // close all pool
//        factory.close(poolObj3);
//        factory.close(poolObj4);
//        
//        assertEquals("obj1", poolObj4.getObj());
//        
//        
//        TestObjectPoolFactory factory = TestObjectPoolFactory.getInstance();
//        PoolObj poolObj = null;
//        try {
//            poolObj = factory.get();
//        } finally {
//            if(poolObj != null) {
//                factory.close(poolObj);
//            }
//        }
//    }
    
    @Test
    @SuppressWarnings("PMD")
    public void executerTest() {
        executer.execute(new Execution<PoolObj, String>() {

            @Override
            public String execute(PoolObj poolObj) {
                System.out.println("poolObj===" + poolObj);
                assertEquals("obj1", poolObj.getObj());
                return poolObj.getObj();
            }

        });
    }
}
