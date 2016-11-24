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
package com.zxy.commons.email;

import java.util.List;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.junit.After;
import org.junit.Before;

import com.google.common.collect.Lists;

/**
 * Send mail test
 * 
 * <p>
 * <a href="SendMailTest.java"><i>View Source</i></a>
 * </p>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class SendMailTest {
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
//    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    /**
     * Send mail test
     * @throws EmailException EmailException
    */
    public void sendMail() throws EmailException {
        String subject = "This is a test email.";
        String htmlBody = "This is a test emails.";
        Map<String, String> properties = null;
        String from = "zhaoxunyong@163.com";
        List<String> toList = Lists.newArrayList("zhaoxunyong@163.com");
        List<String> ccList = null;
        List<String> bccList = null;
        MailMessageUtils.sendMail(subject, htmlBody, properties, from, toList, ccList, bccList);
    }
}
