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
package com.zxy.commons.lang.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.MapSerializer;

/**
 * Kryo seriization utils
 * 
 * <p>
 * <a href="KryoSeriizationUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("PMD.LooseCoupling")
public final class KryoSeriizationUtils {

    private KryoSeriizationUtils() {
    }

    private static ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
//            ((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy())
//                    .setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }
    };

    /**
     * Object serialize.
     * 
     * @param <T> value class
     * @param obj object
     * @return byte[]
     * @throws IOException IOException
    */
    public static <T> byte[] serializationObject(T obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        serializationObject(obj, baos);
        return baos.toByteArray();
    }

    /**
     * Object serialize.
     * 
     * @param <T> value class
     * @param obj object
     * @param os OutputStream
     * @throws IOException IOException
    */
    public static <T> void serializationObject(T obj, OutputStream os) throws IOException {
        Kryo kryo = kryos.get();
//        kryo.setReferences(false);
//        kryo.register(obj.getClass(), new JavaSerializer());

        Output output = new Output(os);
        kryo.writeObject(output, obj);
        output.close();
    }

    /**
     * Object deserialize
     * 
     * @param <T> value class
     * @param obj object
     * @param clazz value class
     * @return Object
    */
    public static <T> T deserializationObject(byte[] obj, Class<T> clazz) {
        ByteArrayInputStream bais = new ByteArrayInputStream(obj);
        return deserializationObject(bais, clazz);
    }

    /**
     * Object deserialize
     * 
     * @param <T> value class
     * @param is InputStream
     * @param clazz value class
     * @return Object
    */
    public static <T> T deserializationObject(InputStream is, Class<T> clazz) {
        Kryo kryo = kryos.get();
//        kryo.setReferences(false);
//        kryo.register(clazz, new JavaSerializer());

        Input input = new Input(is);
        T returnObj = kryo.readObject(input, clazz);
        input.close();
        return returnObj;
    }

    /**
     * List serialize
     * 
     * @param <T> value class
     * @param obj object 
     * @return byte[]
     * @throws IOException IOException
    */
    public static <T> byte[] serializationList(List<T> obj) throws IOException {
        Kryo kryo = kryos.get();
//        kryo.setReferences(false);
//        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
//        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);

//        kryo.register(clazz, new JavaSerializer());
        kryo.register(ArrayList.class, serializer);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.close();
        return baos.toByteArray();
    }

    /**
     * List deserialize
     * 
     * @param <T> value class
     * @param obj byte object
     * @return List
    */
    public static <T> List<T> deserializationList(byte[] obj) {
        Kryo kryo = kryos.get();
//        kryo.setReferences(false);
//        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
//        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);

//        kryo.register(clazz, new JavaSerializer());
        kryo.register(ArrayList.class, serializer);

        ByteArrayInputStream bais = new ByteArrayInputStream(obj);
        Input input = new Input(bais);
        @SuppressWarnings("unchecked")
        List<T> returnList = (List<T>) kryo.readObject(input, ArrayList.class, serializer);
        input.close();
        return returnList;
    }

    /**
     * Set serialize
     * 
     * @param <T> value class
     * @param obj object
     * @return byte[]
     * @throws IOException IOException
    */
    public static <T> byte[] serializationSet(Set<T> obj) throws IOException {
        Kryo kryo = kryos.get();
//        kryo.setReferences(false);
//        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
//        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);

//        kryo.register(clazz, new JavaSerializer());
        kryo.register(HashSet.class, serializer);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.close();
        return baos.toByteArray();

    }

    /**
     * Set deserialize
     * 
     * @param <T> value class
     * @param obj bytes object
     * @return Set
    */
    public static <T> Set<T> deserializationSet(byte[] obj) {
        Kryo kryo = kryos.get();
//        kryo.setReferences(false);
//        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
//        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);

//        kryo.register(clazz, new JavaSerializer());
        kryo.register(HashSet.class, serializer);

        ByteArrayInputStream bais = new ByteArrayInputStream(obj);
        Input input = new Input(bais);
        @SuppressWarnings("unchecked")
        Set<T> returnSet = (Set<T>) kryo.readObject(input, HashSet.class, serializer);
        input.close();
        return returnSet;
    }

    /**
     * Map serialize
     * 
     * @param <K> map key
     * @param <V> map value
     * @param obj map object
     * @return byte[]
     * @throws IOException IOException
    */
    public static <K, V> byte[] serializationMap(Map<K, V> obj) throws IOException {
        Kryo kryo = kryos.get();
//        kryo.setReferences(false);
//        kryo.setRegistrationRequired(true);

        MapSerializer serializer = new MapSerializer();
//        serializer.setKeyClass(String.class, new JavaSerializer());
        serializer.setKeysCanBeNull(false);
//        serializer.setValueClass(kClass, new JavaSerializer());
        serializer.setValuesCanBeNull(true);

//        kryo.register(vClazz, new JavaSerializer());
        kryo.register(HashMap.class, serializer);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.close();
        return baos.toByteArray();
    }

    /**
     * Map deserialize
     * 
     * @param <K> map key
     * @param <V> map value
     * @param obj bytes object
     * @return Map
    */
    public static <K, V> Map<K, V> deserializationMap(byte[] obj) {
        Kryo kryo = kryos.get();
//        kryo.setReferences(false);
//        kryo.setRegistrationRequired(true);

        MapSerializer serializer = new MapSerializer();
//        serializer.setKeyClass(kClass, new JavaSerializer());
        serializer.setKeysCanBeNull(false);
//        serializer.setValueClass(vClazz, new JavaSerializer());
        serializer.setValuesCanBeNull(true);

//        kryo.register(vClazz, new JavaSerializer());
        kryo.register(HashMap.class, serializer);

        ByteArrayInputStream bais = new ByteArrayInputStream(obj);
        Input input = new Input(bais);
        @SuppressWarnings("unchecked")
        Map<K, V> returnMap = (Map<K, V>) kryo.readObject(input, HashMap.class, serializer);
        input.close();
        return returnMap;
    }
}
