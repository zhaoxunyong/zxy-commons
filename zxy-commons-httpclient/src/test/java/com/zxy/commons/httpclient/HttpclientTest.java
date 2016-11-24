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
package com.zxy.commons.httpclient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** 
 * Httpclient test case.
 * 
 * <p>
 * <a href="HttpclientTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
public class HttpclientTest {
    private final static String TEST_FILE = "/tmp/test.png";
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
        FileUtils.deleteQuietly(new File(TEST_FILE));
    }

    @Test
    public void download() throws ClientProtocolException, IOException {
        String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
        HttpclientUtils.download(url, TEST_FILE);
        Assert.assertEquals(true, new File(TEST_FILE).exists());
    }
    
    @Test
    public void get() throws ClientProtocolException, IOException {
        String url = "http://findbugs.sourceforge.net/manual/filter.html";
        String result = HttpclientUtils.get(url);
        Assert.assertNotNull(result);
    }
    
    @Test
    public void post() throws ClientProtocolException, IOException {
        String url = "http://findbugs.sourceforge.net/manual/filter.html";
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("id", "123456"));
        String result = HttpclientUtils.post(url, params);
        Assert.assertNotNull(result);
    }
}
