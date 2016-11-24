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

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import com.google.common.base.Strings;

/**
 * BouncyCastle加解密抽象类
 * 
 * <p>
 * <a href="AbstractRSABcUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractRSABcUtils {
    private final static String ALGORITHM = "RSA/None/NoPadding";
    private final static int SIZE = 16;
    
    public AbstractRSABcUtils() {
        Security.addProvider(new BouncyCastleProvider());
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

    /**
     * 编码
     * 
     * @param msg 需要编码的字符串
     * @return 编码后的字符串
     * @throws IllegalAccessException IllegalAccessException
     * @throws IllegalBlockSizeException IllegalBlockSizeException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws NoSuchProviderException NoSuchProviderException
     * @throws NoSuchPaddingException NoSuchPaddingException
     * @throws InvalidKeySpecException InvalidKeySpecException
     * @throws InvalidKeyException InvalidKeyException
     * @throws BadPaddingException BadPaddingException
    */
    public String encode(String msg) throws IllegalAccessException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, BadPaddingException {
        if (Strings.isNullOrEmpty(msg)) {
            throw new IllegalAccessException("Msg must not be empty!");
        }
        if (msg.length() > 32) {
            throw new IllegalBlockSizeException("Too much data for RSA block, less than or equal 32.");
        }
        // byte[] input = new byte[] { (byte)0xbe, (byte)0xef };
        Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");

        // create the keys
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(new String(getPubKeyText()), SIZE),
                new BigInteger("10001", SIZE));
        RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);

        // encryption step
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] cipherText = cipher.doFinal(msg.getBytes());

        return new String(Hex.encode(cipherText));
    }

    /**
     * 解码
     * 
     * @param msg 需要解码的字符串
     * @return 解码后的字符串
     * @throws IllegalAccessException IllegalAccessException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws NoSuchProviderException NoSuchProviderException
     * @throws NoSuchPaddingException NoSuchPaddingException
     * @throws InvalidKeySpecException InvalidKeySpecException
     * @throws InvalidKeyException InvalidKeyException
     * @throws IllegalBlockSizeException IllegalBlockSizeException
     * @throws BadPaddingException BadPaddingException
     */
    public String decode(String msg) throws IllegalAccessException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (Strings.isNullOrEmpty(msg)) {
            throw new IllegalAccessException("Msg must not be empty!");
        }
        Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");

        // create the keys
        RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(new BigInteger(new String(getPubKeyText()), SIZE),
                new BigInteger(new String(getPriKeyText()), SIZE));
        RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);

        // decryption step
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        byte[] plainText = cipher.doFinal(Hex.decode(msg.getBytes()));

        return new String(plainText);
    }

}
