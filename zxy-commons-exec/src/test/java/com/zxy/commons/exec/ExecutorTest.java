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
package com.zxy.commons.exec;

import java.io.IOException;

import org.apache.commons.exec.OS;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="ExecutorTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class ExecutorTest {
    private final static String CMD = OS.isFamilyWindows()? "ipconfig" : "ifconfig";
    /**
     * setUp
     */
    @Before
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void setUp() {
    }

    /**
     * tearDown
     */
    @After
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void tearDown() {
    }

//    @Test
//    public void checkOS() {
//        Assert.assertEquals(false, OS.isFamilyWindows());
//        Assert.assertEquals(true, OS.isFamilyMac());
//    }
    
    @Test
    public void exec() throws InterruptedException, IOException {
        ExecutorResult executorResult = CmdExecutor.exec(CMD);
        System.out.println("executorResult:"+executorResult);
        Assert.assertEquals("", "");
    }
    
    @Test
    public void execAsyc() throws InterruptedException, IOException {
        CmdExecutor.execAsyc(CMD);
        Assert.assertEquals("", "");
    }
}
