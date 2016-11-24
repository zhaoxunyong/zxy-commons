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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Http client工具类，包括文件下载，post，get等方法。
 * 
 * <p>
 * <a href="HttpclientUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class HttpclientUtils {
//    private final static Logger LOGGER = LoggerFactory.getLogger(HttpclientUtils.class);

    private HttpclientUtils() {
    }

    /**
     * 通过url地址下载文件
     * 
     * @param url url地址
     * @param outputFile 保存到本地的文件路径
     * @return 是否下载成功
     * @throws ClientProtocolException ClientProtocolException
     * @throws IOException IOException
     */
    public static boolean download(String url, String outputFile) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;

        InputStream in = null;
        OutputStream out = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            in = entity.getContent();

            File file = new File(outputFile);
            out = new FileOutputStream(file);
            IOUtils.copy(in, out);
            return file.exists();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpResponse);
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpClient);
        }
    }

    /**
     * 通过url地址下载文件，返回一个byte[]
     * 
     * @param url url地址
     * @return byte[]
     * @throws IOException IOException
     */
    public static byte[] download(String url) throws IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            in = entity.getContent();

            out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            return out.toByteArray();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpResponse);
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpClient);
        }
    }

    /**
     * Post url
     * 
     * @param url url地址
     * @param params {@link org.apache.http.NameValuePair} params
     *            <p>
     *            example:{@code params.add(new BasicNameValuePair("id", "123456"));}
     * @return 返回post获取后的结果
     * @throws ClientProtocolException ClientProtocolException
     * @throws IOException IOException
     * @see org.apache.http.NameValuePair
     * @see org.apache.http.message.BasicNameValuePair
     */
    public static String post(String url, List<NameValuePair> params) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);

            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, Charsets.UTF_8);
            httppost.setEntity(uefEntity);

            httpResponse = httpClient.execute(httppost);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity, Charsets.UTF_8);
        } finally {
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpResponse);
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpClient);
        }
    }

    /**
     * Get url
     * 
     * @param url url地址
     * @return 返回post获取后的结果
     * @throws ClientProtocolException ClientProtocolException
     * @throws IOException IOException
     */
    public static String get(String url) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity, Charsets.UTF_8);
        } finally {
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpResponse);
            org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpClient);
        }
    }
}
