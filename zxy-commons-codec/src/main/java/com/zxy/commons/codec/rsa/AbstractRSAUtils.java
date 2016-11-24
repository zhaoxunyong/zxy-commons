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
package com.zxy.commons.codec.rsa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA加解密抽象类
 * 
 * <p>
 * <a href="RSAUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
 */
//@SuppressFBWarnings(value="DM_DEFAULT_ENCODING")
public abstract class AbstractRSAUtils {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /** ALGORITHM */
    private final static String ALGORITHM = "RSA";
    /** KEY_SIZE */
    private final static int KEY_SIZE = 1024;

    private Provider provider;

    protected void setProvider(Provider provider) {
        this.provider = provider;
    }
    
    /**
     * 生成公钥与私钥
     * 
     * @param pubFile public file
     * @param priFile private file
     * @throws IOException IOException
     */
    @SuppressWarnings("PMD.PrematureDeclaration")
    protected void generater(File pubFile, File priFile) throws IOException {
        try {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);
            SecureRandom secrand = new SecureRandom();
            keygen.initialize(KEY_SIZE, secrand);
            KeyPair keys = keygen.genKeyPair();
            PublicKey pubkey = keys.getPublic();
            PrivateKey prikey = keys.getPrivate();
            byte[] priKey = Base64.encodeBase64(prikey.getEncoded());
            byte[] pubKey = Base64.encodeBase64(pubkey.getEncoded());
            if(pubFile.exists()){
                throw new IOException(pubFile.getPath()+" is exist!");
            }
            if(priFile.exists()){
                throw new IOException(priFile.getPath()+" is exist!");
            }
            OutputStream pubOutput = new FileOutputStream(pubFile);
            try {
                IOUtils.write(pubKey, pubOutput);
            } finally {
                IOUtils.closeQuietly(pubOutput);
            }
            OutputStream priOutput = new FileOutputStream(priFile);
            try {
                IOUtils.write(priKey, priOutput);
            } finally {
                IOUtils.closeQuietly(priOutput);
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("生成密钥对失败", e);
        }
    }

    /**
     * 
     * 加密
     * 
     * @param info 需要加密的字符串
     * @return 加密后字符串
     * @throws GeneralSecurityException GeneralSecurityException
     */
    public String encode(String info) throws GeneralSecurityException {
        // 生成token信息
        byte[] pubKeyText = this.getPubKeyText();
        X509EncodedKeySpec bobPKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(pubKeyText));
        KeyFactory keyFactory = null;
        if (provider == null) {
            keyFactory = KeyFactory.getInstance(ALGORITHM);
        } else {
            keyFactory = KeyFactory.getInstance(ALGORITHM, provider);
        }
        // 取公钥匙对象
        PublicKey pubkey = keyFactory.generatePublic(bobPKeySpec);
        // 获得一个公鈅加密类Cipher，ECB是加密方式，PKCS5Padding是填充方法
        Cipher cipher = null;
        if (provider == null) {
            cipher = Cipher.getInstance(ALGORITHM);
        } else {
            cipher = Cipher.getInstance(ALGORITHM, provider);
        }
        // 使用私鈅加密
        cipher.init(Cipher.ENCRYPT_MODE, pubkey);
        byte[] cipherText = cipher.doFinal(info.getBytes());
        return new String(Base64.encodeBase64(cipherText));
    }

    /**
     * 
     * 解密
     * 
     * @param info 需要解密的字符串
     * @return 解密后字符串
     * @throws GeneralSecurityException GeneralSecurityException
     */
    public String decode(String info) throws GeneralSecurityException {
        byte[] priKeyText = this.getPriKeyText();
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(priKeyText));
        KeyFactory keyFactory = null;
        if (provider == null) {
            keyFactory = KeyFactory.getInstance(ALGORITHM);
        } else {
            keyFactory = KeyFactory.getInstance(ALGORITHM, provider);
        }
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(priPKCS8);
        // 获得一个私鈅加密类Cipher，ECB是加密方式，PKCS5Padding是填充方法
        Cipher cipher = null;
        if (provider == null) {
            cipher = Cipher.getInstance(ALGORITHM);
        } else {
            cipher = Cipher.getInstance(ALGORITHM, provider);
        }
        // 使用私钥解密
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        byte[] newPlainText = cipher.doFinal(Base64.decodeBase64(info.getBytes()));
        return new String(newPlainText);
    }

    /**
     * 
     * 获取私钥
     * 
     * @return 私钥
     */
    protected abstract byte[] getPriKeyText();

    /**
     * 
     * 获取公钥
     * 
     * @return 公钥
     */
    protected abstract byte[] getPubKeyText();

}
