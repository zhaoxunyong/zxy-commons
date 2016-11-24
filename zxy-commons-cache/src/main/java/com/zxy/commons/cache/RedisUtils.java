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
package com.zxy.commons.cache;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisListCommands.Position;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisServerCommands.MigrateOption;
import org.springframework.data.redis.connection.RedisServerCommands.ShutdownOption;
import org.springframework.data.redis.connection.RedisStringCommands.BitOperation;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.connection.RedisZSetCommands.Aggregate;
import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.connection.Subscription;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.core.types.RedisClientInfo;

import com.zxy.commons.json.JsonUtils;
import com.zxy.commons.spring.SpringBeanUtils;

/** 
 * Redis工具类
 * 
 * <p>
 * 必须在spring配置中引入：
 * <p>
 * &lt;import resource=&quot;classpath:commons-spring.xml&quot; /&gt;
 * <p>{@code RedisTemplate}的id名称必须为redisTemplate。
 * <p>适用于只有一个redisTemplate的项目，如果项目中有多个redisTemplate，请使用{@code RedisHelper}类注入。
 * <p>
 * <a href="RedisUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
@Deprecated
public final class RedisUtils {
    private static RedisTemplate<?, ?> redisTemplate = SpringBeanUtils.getBean(RedisTemplate.class);
    
    private RedisUtils() {}
    
    /**
     * Get the value of {@code key}.
     * <p>
     * See http://redis.io/commands/get
     * 
     * @param key must not be {@literal null}.
     * @return value
     */
    public static byte[] get(byte[] key) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.get(key);
            }
        });
    }
    
    /**
     * Get the value of {@code key}.
     * <p>
     * See http://redis.io/commands/get
     * 
     * @param key must not be {@literal null}.
     * @return value
    */
    public static String get(String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redis) throws DataAccessException {
                byte[] value = redis.get(key.getBytes());
                if(value == null) {
                    return null;
                }
                return new String(value);
            }
        });
    }
    
    /**
     * Get the value of {@code key}.
     * <p>
     * See http://redis.io/commands/get
     * 
     * @param <T> return object type
     * @param key must not be {@literal null}.
     * @param callback callback
     * @return value
    */
    public static <T> T get(byte[] key, RedisTransferCallback<T> callback) {
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection redis) throws DataAccessException {
                byte[] value = redis.get(key);
                if(value == null) {
                    return null;
                }
                return callback.transfer(value);
            }
        });
    }
    
    /**
     * Get the value of {@code key}.
     * <p>
     * See http://redis.io/commands/get
     * 
     * @param <T> return object type
     * @param key must not be {@literal null}.
     * @param clazz clazz
     * @return value
    */
    public static <T> T getObject4Json(byte[] key, Class<T> clazz) {
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection redis) throws DataAccessException {
//                return callback.transfer(redis.get(key));
                byte[] value = redis.get(key);
                if(value == null) {
                    return null;
                }
                return JsonUtils.toObject(new String(value), clazz);
            }
        });
    }
    
    /**
     * Get the value of {@code key}.
     * <p>
     * See http://redis.io/commands/get
     * 
     * @param <T> return object type
     * @param key must not be {@literal null}.
     * @param clazz clazz
     * @return value
    */
    public static <T> List<T> getList4Json(byte[] key, Class<T> clazz) {
        return redisTemplate.execute(new RedisCallback<List<T>>() {
            @Override
            public List<T> doInRedis(RedisConnection redis) throws DataAccessException {
//                return callback.transfer(redis.get(key));
                byte[] value = redis.get(key);
                if(value == null) {
                    return null;
                }
                return JsonUtils.toList(new String(value), clazz);
            }
        });
    }
    
    /**
     * Get the value of {@code key}.
     * <p>
     * See http://redis.io/commands/get
     * 
     * @param <T> return object type
     * @param key must not be {@literal null}.
     * @param clazz clazz
     * @return value
    */
    public static <T> Set<T> getSet4Json(byte[] key, Class<T> clazz) {
        return redisTemplate.execute(new RedisCallback<Set<T>>() {
            @Override
            public Set<T> doInRedis(RedisConnection redis) throws DataAccessException {
//                return callback.transfer(redis.get(key));
                byte[] value = redis.get(key);
                if(value == null) {
                    return null;
                }
                return JsonUtils.toSet(new String(value), clazz);
            }
        });
    }
    
    /**
     * Get the value of {@code key}.
     * <p>
     * See http://redis.io/commands/get
     * 
     * @param <K> This is the key parameter
     * @param <V> This is the value parameter
     * @param key must not be {@literal null}.
     * @param keyClass key class
     * @param valueClass value class
     * @return value
    */
    public static <K, V> Map<K, V> getMap4Json(byte[] key, Class<K> keyClass, Class<V> valueClass) {
        return redisTemplate.execute(new RedisCallback<Map<K, V>>() {
            @Override
            public Map<K, V> doInRedis(RedisConnection redis) throws DataAccessException {
//                return callback.transfer(redis.get(key));
                byte[] value = redis.get(key);
                if(value == null) {
                    return null;
                }
                return JsonUtils.toMap(new String(value), keyClass, valueClass);
            }
        });
    }

    /**
     * Set value of {@code key} and return its old value.
     * <p>
     * See http://redis.io/commands/getset
     * 
     * @param key must not be {@literal null}.
     * @param value value
     * @return byte[]
     */
    public static byte[] getSet(byte[] key, byte[] value) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getSet(key, value);
            }
        });
    }
    
    /**
     * Get the values of all given {@code keys}.
     * <p>
     * See http://redis.io/commands/mget
     * 
     * @param keys keys
     * @return List<byte[]>
     */
    public static List<byte[]> mGet(byte[]... keys) {
        return redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.mGet(keys);
            }
        });
    }

    /**
     * Set {@code value} for {@code key}.
     * <p>
     * See http://redis.io/commands/set
     * 
     * @param key must not be {@literal null}.
     * @param value must not be {@literal null}.
     */
    public static void set(byte[] key, byte[] value) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.set(key, value);
                return null;
            }
        });
    }
    
    /**
     * Set {@code value} for {@code key}.
     * <p>
     * See http://redis.io/commands/set
     * 
     * @param <T> value class type
     * @param key must not be {@literal null}.
     * @param value must not be {@literal null}.
     */
    public static <T> void set4Json(byte[] key, T value) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                byte[] jsonValue = JsonUtils.toJson(value).getBytes();
                redis.set(key, jsonValue);
                return null;
            }
        });
    }
    
    /**
     * Set {@code value} for {@code key} applying timeouts from {@code expiration} if set and inserting/updating values
     * depending on {@code option}.
     * <p>
     * See http://redis.io/commands/set
     *
     * @param key must not be {@literal null}.
     * @param value must not be {@literal null}.
     * @param expiration can be {@literal null}. Defaulted to {@link Expiration#persistent()}.
     * @param option can be {@literal null}. Defaulted to {@link SetOption#UPSERT}.
     * @since 1.7
     */
    public static void set(byte[] key, byte[] value, Expiration expiration, SetOption option) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.set(key, value, expiration, option);
                return null;
            }
        });
    }
    
    /**
     * Set {@code value} for {@code key}, only if {@code key} does not exist.
     * <p>
     * See http://redis.io/commands/setnx
     * 
     * @param key must not be {@literal null}.
     * @param value must not be {@literal null}.
     * @return Boolean
     */
    public static Boolean setNX(byte[] key, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.setNX(key, value);
            }
        });
    }
    
    /**
     * Set the {@code value} and expiration in {@code seconds} for {@code key}.
     * <p>
     * See http://redis.io/commands/setex
     * 
     * @param key must not be {@literal null}.
     * @param seconds exprise time
     * @param value must not be {@literal null}.
     */
    public static void setEx(byte[] key, long seconds, byte[] value) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.setEx(key, seconds, value);
                return null;
            }
        });
    }
    
    /**
     * Set the {@code value} and expiration in {@code milliseconds} for {@code key}.
     * <p>
     * See http://redis.io/commands/psetex
     * 
     * @param key must not be {@literal null}.
     * @param milliseconds exprise milliseconds
     * @param value must not be {@literal null}.
     * @since 1.3
     */
    public static void pSetEx(byte[] key, long milliseconds, byte[] value) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.pSetEx(key, milliseconds, value);
                return null;
            }
        });
    }

    /**
     * Set multiple keys to multiple values using key-value pairs provided in {@code tuple}.
     * <p>
     * See http://redis.io/commands/mset
     * 
     * @param tuple tuple
     */
    public static void mSet(Map<byte[], byte[]> tuple) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.mSet(tuple);
                return null;
            }
        });
    }

    /**
     * Set multiple keys to multiple values using key-value pairs provided in {@code tuple} only if the provided key does
     * not exist.
     * <p>
     * See http://redis.io/commands/msetnx
     * 
     * @param tuple tuple
     * @return Boolean
     */
    public static Boolean mSetNX(Map<byte[], byte[]> tuple) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.mSetNX(tuple);
            }
        });
    }
    
    /**
     * Increment value of {@code key} by 1.
     * <p>
     * See http://redis.io/commands/incr
     * 
     * @param key must not be {@literal null}.
     * @return Long
     */
    public static Long incr(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.incr(key);
            }
        });
    }

    /**
     * Increment value of {@code key} by {@code value}.
     * <p>
     * See http://redis.io/commands/incrby
     * 
     * @param key must not be {@literal null}.
     * @param value value
     * @return Long
     */
    public static Long incrBy(byte[] key, long value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.incrBy(key, value);
            }
        });
    }

    /**
     * Increment value of {@code key} by {@code value}.
     * <p>
     * See http://redis.io/commands/incrbyfloat
     * 
     * @param key must not be {@literal null}.
     * @param value value
     * @return Double
     */
    public static Double incrBy(byte[] key, double value) {
        return redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.incrBy(key, value);
            }
        });
    }

    /**
     * Decrement value of {@code key} by 1.
     * <p>
     * See http://redis.io/commands/decr
     * 
     * @param key must not be {@literal null}.
     * @return Long
     */
    public static Long decr(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.decr(key);
            }
        });
    }

    /**
     * Increment value of {@code key} by {@code value}.
     * <p>
     * See http://redis.io/commands/decrby
     * 
     * @param key must not be {@literal null}.
     * @param value value
     * @return Long
     */
    public static Long decrBy(byte[] key, long value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.decrBy(key, value);
            }
        });
    }

    /**
     * Append a {@code value} to {@code key}.
     * <p>
     * See http://redis.io/commands/append
     * 
     * @param key must not be {@literal null}.
     * @param value value
     * @return Long
     */
    public static Long append(byte[] key, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.append(key, value);
            }
        });
    }

    /**
     * Get a substring of value of {@code key} between {@code begin} and {@code end}.
     * <p>
     * See http://redis.io/commands/getrange
     * 
     * @param key must not be {@literal null}.
     * @param begin begin
     * @param end end
     * @return byte[]
     */
    public static byte[] getRange(byte[] key, long begin, long end) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getRange(key, begin, end);
            }
        });
    }

    /**
     * Overwrite parts of {@code key} starting at the specified {@code offset} with given {@code value}.
     * <p>
     * See http://redis.io/commands/setrange
     * 
     * @param key must not be {@literal null}.
     * @param value value
     * @param offset offset
     */
    public static void setRange(byte[] key, byte[] value, long offset) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.setRange(key, value, offset);
                return null;
            }
        });
    }

    /**
     * Get the bit value at {@code offset} of value at {@code key}.
     * <p>
     * See http://redis.io/commands/getbit
     * 
     * @param key must not be {@literal null}.
     * @param offset offset
     * @return Boolean
     */
    public static Boolean getBit(byte[] key, long offset) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getBit(key, offset);
            }
        });
    }

    /**
     * Sets the bit at {@code offset} in value stored at {@code key}.
     * <p>
     * See http://redis.io/commands/setbit
     * 
     * @param key must not be {@literal null}.
     * @param offset offset
     * @param value value
     * @return the original bit value stored at {@code offset}.
     */
    public static Boolean setBit(byte[] key, long offset, boolean value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.setBit(key, offset, value);
            }
        });
    }

    /**
     * Count the number of set bits (population counting) in value stored at {@code key}.
     * <p>
     * See http://redis.io/commands/bitcount
     * 
     * @param key must not be {@literal null}.
     * @return Long
     */
    public static Long bitCount(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.bitCount(key);
            }
        });
    }

    /**
     * Count the number of set bits (population counting) of value stored at {@code key} between {@code begin} and
     * {@code end}.
     * <p>
     * See http://redis.io/commands/bitcount
     * 
     * @param key must not be {@literal null}.
     * @param begin begin
     * @param end end
     * @return Long
     */
    public static Long bitCount(byte[] key, long begin, long end) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.bitCount(key, begin, end);
            }
        });
    }

    /**
     * Perform bitwise operations between strings.
     * <p>
     * See http://redis.io/commands/bitop
     * 
     * @param op op
     * @param destination destination
     * @param keys keys
     * @return Long
     */
    public static Long bitOp(BitOperation op, byte[] destination, byte[]... keys) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.bitOp(op, destination, keys);
            }
        });
    }

    /**
     * Get the length of the value stored at {@code key}.
     * <p>
     * See http://redis.io/commands/strlen
     * 
     * @param key must not be {@literal null}.
     * @return Long
     */
    public static Long strLen(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.strLen(key);
            }
        });
    }

    /**
     * Expire key for seconds
     * <p>
     * See http://redis.io/commands/strlen
     * 
     * @param key must not be {@literal null}.
     * @param seconds seconds time
     * @return Long
     */
    public static Boolean expire(byte[] key, int seconds) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.expire(key, seconds);
            }
        });
    }

    /**
     * 'Native' or 'raw' execution of the given command along-side the given arguments. The command is executed as is,
     * with as little 'interpretation' as possible - it is up to the caller to take care of any processing of arguments or
     * the result.
     * 
     * @param command Command to execute
     * @param args Possible command arguments (may be null)
     * @return execution result.
     */
    public static Object execute(String command, byte[]... args) {
        return redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.execute(command, args);
            }
        });
    }
    
    /**
     * Determine if given {@code key} exists.
     * <p>
     * See http://redis.io/commands/exists
     * 
     * @param key key
     * @return Boolean
     */
    public static Boolean exists(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.exists(key);
            }
        });
    }

    /**
     * Delete given {@code keys}.
     * <p>
     * See http://redis.io/commands/del
     * 
     * @param keys keys
     * @return The number of keys that were removed.
     */
    public static Long del(byte[]... keys) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.del(keys);
            }
        });
    }

    /**
     * Determine the type stored at {@code key}.
     * <p>
     * See http://redis.io/commands/type
     * 
     * @param key key
     * @return DataType
     */
    public static DataType type(byte[] key) {
        return redisTemplate.execute(new RedisCallback<DataType>() {
            @Override
            public DataType doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.type(key);
            }
        });
    }

    /**
     * Find all keys matching the given {@code pattern}.
     * <p>
     * See http://redis.io/commands/keys
     * 
     * @param pattern pattern
     * @return Set<byte[]>
     */
    public static Set<byte[]> keys(byte[] pattern) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.keys(pattern);
            }
        });
    }

    /**
     * Use a {@link Cursor} to iterate over keys.
     * <p>
     * See http://redis.io/commands/scan
     * 
     * @param options options
     * @return Cursor<byte[]>
     * @since 1.4
     */
    public static Cursor<byte[]> scan(ScanOptions options) {
        return redisTemplate.execute(new RedisCallback<Cursor<byte[]>>() {
            @Override
            public Cursor<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.scan(options);
            }
        });
    }

    /**
     * Return a random key from the keyspace.
     * <p>
     * See http://redis.io/commands/randomkey
     * 
     * @return byte[]
     */
    public static byte[] randomKey() {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.randomKey();
            }
        });
    }

    /**
     * Rename key {@code oleName} to {@code newName}.
     * <p>
     * See http://redis.io/commands/rename
     * 
     * @param oldName oldName
     * @param newName newName
     */
    public static void rename(byte[] oldName, byte[] newName) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.rename(oldName, newName);
                return null;
            }
        });
    }

    /**
     * Rename key {@code oleName} to {@code newName} only if {@code newName} does not exist.
     * <p>
     * See http://redis.io/commands/renamenx
     * 
     * @param oldName oldName
     * @param newName newName
     * @return Boolean
     */
    public static Boolean renameNX(byte[] oldName, byte[] newName) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.renameNX(oldName, newName);
            }
        });
    }

    /**
     * Set time to live for given {@code key} in seconds.
     * <p>
     * See http://redis.io/commands/expire
     * 
     * @param key key
     * @param seconds seconds
     * @return Boolean
     */
    public static Boolean expire(byte[] key, long seconds) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.expire(key, seconds);
            }
        });
    }

    /**
     * Set time to live for given {@code key} in milliseconds.
     * <p>
     * See http://redis.io/commands/pexpire
     * 
     * @param key key
     * @param millis millis
     * @return Boolean
     */
    public static Boolean pExpire(byte[] key, long millis) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.pExpire(key, millis);
            }
        });
    }

    /**
     * Set the expiration for given {@code key} as a {@literal UNIX} timestamp.
     * <p>
     * See http://redis.io/commands/expireat
     * 
     * @param key key
     * @param unixTime unixTime
     * @return Boolean
     */
    public static Boolean expireAt(byte[] key, long unixTime) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.expireAt(key, unixTime);
            }
        });
    }

    /**
     * Set the expiration for given {@code key} as a {@literal UNIX} timestamp in milliseconds.
     * <p>
     * See http://redis.io/commands/pexpireat
     * 
     * @param key key
     * @param unixTimeInMillis unixTimeInMillis
     * @return Boolean
     */
    public static Boolean pExpireAt(byte[] key, long unixTimeInMillis) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.pExpireAt(key, unixTimeInMillis);
            }
        });
    }

    /**
     * Remove the expiration from given {@code key}.
     * <p>
     * See http://redis.io/commands/persist
     * 
     * @param key key
     * @return Boolean
     */
    public static Boolean persist(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.persist(key);
            }
        });
    }

    /**
     * Move given {@code key} to database with {@code index}.
     * <p>
     * See http://redis.io/commands/move
     * 
     * @param key key
     * @param dbIndex dbIndex
     * @return Boolean
     */
    public static Boolean move(byte[] key, int dbIndex) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.move(key, dbIndex);
            }
        });
    }

    /**
     * Get the time to live for {@code key} in seconds.
     * <p>
     * See http://redis.io/commands/ttl
     * 
     * @param key key
     * @return Long
     */
    public static Long ttl(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.ttl(key);
            }
        });
    }

    /**
     * Get the time to live for {@code key} in milliseconds.
     * <p>
     * See http://redis.io/commands/pttl
     * 
     * @param key key
     * @return Long
     */
    public static Long pTtl(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.pTtl(key);
            }
        });
    }

    /**
     * Sort the elements for {@code key}.
     * <p>
     * See http://redis.io/commands/sort
     * 
     * @param key key
     * @param params params
     * @return List<byte[]>
     */
    public static List<byte[]> sort(byte[] key, SortParameters params) {
        return redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sort(key, params);
            }
        });
    }

    /**
     * Sort the elements for {@code key} and store result in {@code storeKey}.
     * <p>
     * See http://redis.io/commands/sort
     * 
     * @param key key
     * @param params params
     * @param storeKey storeKey
     * @return Long
     */
    public static Long sort(byte[] key, SortParameters params, byte[] storeKey) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sort(key, params, storeKey);
            }
        });
    }

    /**
     * Retrieve serialized version of the value stored at {@code key}.
     * <p>
     * See http://redis.io/commands/dump
     * 
     * @param key key
     * @return byte[]
     */
    public static byte[] dump(byte[] key) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.dump(key);
            }
        });
    }

    /**
     * Create {@code key} using the {@code serializedValue}, previously obtained using {@link #dump(byte[])}.
     * <p>
     * See http://redis.io/commands/restore
     * 
     * @param key key
     * @param ttlInMillis ttlInMillis
     * @param serializedValue serializedValue
     */
    public static void restore(byte[] key, long ttlInMillis, byte[] serializedValue) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.restore(key, ttlInMillis, serializedValue);
                return null;
            }
        });
    }

    /**
     * Append {@code values} to {@code key}.
     * <p>
     * See http://redis.io/commands/rpush
     * 
     * @param key key
     * @param values values
     * @return Long
     */
    public static Long rPush(byte[] key, byte[]... values) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.rPush(key, values);
            }
        });
    }

    /**
     * Prepend {@code values} to {@code key}.
     * <p>
     * See http://redis.io/commands/lpush
     * 
     * @param key key
     * @param values values
     * @return Long
     */
    public static Long lPush(byte[] key, byte[]... values) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lPush(key, values);
            }
        });
    }

    /**
     * Append {@code} values to {@code key} only if the list exists.
     * <p>
     * See http://redis.io/commands/rpushx
     * 
     * @param key key
     * @param value value
     * @return Long
     */
    public static Long rPushX(byte[] key, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.rPushX(key, value);
            }
        });
    }

    /**
     * Prepend {@code values} to {@code key} only if the list exists.
     * <p>
     * See http://redis.io/commands/lpushx
     * 
     * @param key key
     * @param value value
     * @return lPushX
     */
    public static Long lPushX(byte[] key, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lPushX(key, value);
            }
        });
    }

    /**
     * Get the size of list stored at {@code key}.
     * <p>
     * See http://redis.io/commands/llen
     * 
     * @param key key
     * @return Long
     */
    public static Long lLen(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lLen(key);
            }
        });
    }

    /**
     * Get elements between {@code begin} and {@code end} from list at {@code key}.
     * <p>
     * See http://redis.io/commands/lrange
     * 
     * @param key key
     * @param begin begin
     * @param end end
     * @return List<byte[]>
     */
    public static List<byte[]> lRange(byte[] key, long begin, long end) {
        return redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lRange(key, begin, end);
            }
        });
    }

    /**
     * Trim list at {@code key} to elements between {@code begin} and {@code end}.
     * <p>
     * See http://redis.io/commands/ltrim
     * 
     * @param key key
     * @param begin begin
     * @param end end
     */
    public static void lTrim(byte[] key, long begin, long end) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.lTrim(key, begin, end);
                return null;
            }
        });
    }

    /**
     * Get element at {@code index} form list at {@code key}.
     * <p>
     * See http://redis.io/commands/lindex
     * 
     * @param key key
     * @param index index
     * @return byte[]
     */
    public static byte[] lIndex(byte[] key, long index) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lIndex(key, index);
            }
        });
    }

    /**
     * Insert {@code value} {@link Position#BEFORE} or {@link Position#AFTER} existing {@code pivot} for {@code key}.
     * <p>
     * See http://redis.io/commands/linsert
     * 
     * @param key key
     * @param where where
     * @param pivot pivot
     * @param value value
     * @return Long
     */
    public static Long lInsert(byte[] key, Position where, byte[] pivot, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lInsert(key, where, pivot, value);
            }
        });
    }

    /**
     * Set the {@code value} list element at {@code index}.
     * <p>
     * See http://redis.io/commands/lset
     * 
     * @param key key
     * @param index index
     * @param value value
     */
    public static void lSet(byte[] key, long index, byte[] value) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.lSet(key, index, value);
                return null;
            }
        });
    }

    /**
     * Removes the first {@code count} occurrences of {@code value} from the list stored at {@code key}.
     * <p>
     * See http://redis.io/commands/lrem
     * 
     * @param key key
     * @param count count
     * @param value value
     * @return Long
     */
    public static Long lRem(byte[] key, long count, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lRem(key, count, value);
            }
        });
    }

    /**
     * Removes and returns first element in list stored at {@code key}.
     * <p>
     * See http://redis.io/commands/lpop
     * 
     * @param key key
     * @return byte[]
     */
    public static byte[] lPop(byte[] key) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lPop(key);
            }
        });
    }

    /**
     * Removes and returns last element in list stored at {@code key}.
     * <p>
     * See http://redis.io/commands/rpop
     * 
     * @param key key
     * @return byte[]
     */
    public static byte[] rPop(byte[] key) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.rPop(key);
            }
        });
    }

    /**
     * Removes and returns first element from lists stored at {@code keys} (see: {@link #lPop(byte[])}). <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     * <p>
     * See http://redis.io/commands/blpop
     * 
     * @param timeout timeout
     * @param keys keys
     * @return List<byte[]>
     */
    public static List<byte[]> bLPop(int timeout, byte[]... keys) {
        return redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.bLPop(timeout, keys);
            }
        });
    }

    /**
     * Removes and returns last element from lists stored at {@code keys} (see: {@link #rPop(byte[])}). <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     * <p>
     * See http://redis.io/commands/brpop
     * 
     * @param timeout timeout
     * @param keys keys
     * @return List<byte[]>
     */
    public static List<byte[]> bRPop(int timeout, byte[]... keys) {
        return redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.bRPop(timeout, keys);
            }
        });
    }

    /**
     * Remove the last element from list at {@code srcKey}, append it to {@code dstKey} and return its value.
     * <p>
     * See http://redis.io/commands/rpoplpush
     * 
     * @param srcKey srcKey
     * @param dstKey dstKey
     * @return byte[]
     */
    public static byte[] rPopLPush(byte[] srcKey, byte[] dstKey) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.rPopLPush(srcKey, dstKey);
            }
        });
    }

    /**
     * Remove the last element from list at {@code srcKey}, append it to {@code dstKey} and return its value (see
     * {@link #rPopLPush(byte[], byte[])}). <br>
     * <b>Blocks connection</b> until element available or {@code timeout} reached.
     * <p>
     * See http://redis.io/commands/brpoplpush
     * 
     * @param timeout timeout
     * @param srcKey srcKey
     * @param dstKey dstKey
     * @return byte[]
     */
    public static byte[] bRPopLPush(int timeout, byte[] srcKey, byte[] dstKey) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.bRPopLPush(timeout, srcKey, dstKey);
            }
        });
    }

    /**
     * Add given {@code values} to set at {@code key}.
     * <p>
     * See http://redis.io/commands/sadd
     * 
     * @param key key
     * @param values values
     * @return Long
     */
    public static Long sAdd(byte[] key, byte[]... values) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sAdd(key, values);
            }
        });
    }

    /**
     * Remove given {@code values} from set at {@code key} and return the number of removed elements.
     * <p>
     * See http://redis.io/commands/srem
     * 
     * @param key key
     * @param values values
     * @return Long
     */
    public static Long sRem(byte[] key, byte[]... values) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sRem(key, values);
            }
        });
    }

    /**
     * Remove and return a random member from set at {@code key}.
     * <p>
     * See http://redis.io/commands/spop
     * 
     * @param key key
     * @return byte[]
     */
    public static byte[] sPop(byte[] key) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sPop(key);
            }
        });
    }

    /**
     * Move {@code value} from {@code srcKey} to {@code destKey}
     * <p>
     * See http://redis.io/commands/smove
     * 
     * @param srcKey srcKey
     * @param destKey destKey
     * @param value value
     * @return Boolean
     */
    public static Boolean sMove(byte[] srcKey, byte[] destKey, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sMove(srcKey, destKey, value);
            }
        });
    }

    /**
     * Get size of set at {@code key}.
     * <p>
     * See http://redis.io/commands/scard
     * 
     * @param key key
     * @return Long
     */
    public static Long sCard(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sCard(key);
            }
        });
    }

    /**
     * Check if set at {@code key} contains {@code value}.
     * <p>
     * See http://redis.io/commands/sismember
     * 
     * @param key key
     * @param value value
     * @return Boolean
     */
    public static Boolean sIsMember(byte[] key, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sIsMember(key, value);
            }
        });
    }

    /**
     * Returns the members intersecting all given sets at {@code keys}.
     * <p>
     * See http://redis.io/commands/sinter
     * 
     * @param keys keys
     * @return Set<byte[]>
     */
    public static Set<byte[]> sInter(byte[]... keys) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sInter(keys);
            }
        });
    }

    /**
     * Intersect all given sets at {@code keys} and store result in {@code destKey}.
     * <p>
     * See http://redis.io/commands/sinterstore
     * 
     * @param destKey destKey
     * @param keys keys
     * @return Long
     */
    public static Long sInterStore(byte[] destKey, byte[]... keys) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sInterStore(destKey, keys);
            }
        });
    }

    /**
     * Union all sets at given {@code keys}.
     * <p>
     * See http://redis.io/commands/sunion
     * 
     * @param keys keys
     * @return Set<byte[]>
     */
    public static Set<byte[]> sUnion(byte[]... keys) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sUnion(keys);
            }
        });
    }

    /**
     * Union all sets at given {@code keys} and store result in {@code destKey}.
     * <p>
     * See http://redis.io/commands/sunionstore
     * 
     * @param destKey destKey
     * @param keys keys
     * @return Long
     */
    public static Long sUnionStore(byte[] destKey, byte[]... keys) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sUnionStore(destKey, keys);
            }
        });
    }

    /**
     * Diff all sets for given {@code keys}.
     * <p>
     * See http://redis.io/commands/sdiff
     * 
     * @param keys keys
     * @return Set<byte[]>
     */
    public static Set<byte[]> sDiff(byte[]... keys) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sDiff(keys);
            }
        });
    }

    /**
     * Diff all sets for given {@code keys} and store result in {@code destKey}
     * <p>
     * See http://redis.io/commands/sdiffstore
     * 
     * @param destKey destKey
     * @param keys keys
     * @return Long
     */
    public static Long sDiffStore(byte[] destKey, byte[]... keys) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sDiffStore(destKey, keys);
            }
        });
    }

    /**
     * Get all elements of set at {@code key}.
     * <p>
     * See http://redis.io/commands/smembers
     * 
     * @param key key
     * @return Set<byte[]>
     */
    public static Set<byte[]> sMembers(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sMembers(key);
            }
        });
    }

    /**
     * Get random element from set at {@code key}.
     * <p>
     * See http://redis.io/commands/srandmember
     * 
     * @param key key
     * @return byte[]
     */
    public static byte[] sRandMember(byte[] key) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sRandMember(key);
            }
        });
    }

    /**
     * Get {@code count} random elements from set at {@code key}.
     * <p>
     * See http://redis.io/commands/srandmember
     * 
     * @param key key
     * @param count count
     * @return List<byte[]>
     */
    public static List<byte[]> sRandMember(byte[] key, long count) {
        return redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sRandMember(key, count);
            }
        });
    }

    /**
     * Use a {@link Cursor} to iterate over elements in set at {@code key}.
     * <p>
     * See http://redis.io/commands/scan
     * 
     * @since 1.4
     * @param key key
     * @param options options
     * @return Cursor<byte[]>
     */
    public static Cursor<byte[]> sScan(byte[] key, ScanOptions options) {
        return redisTemplate.execute(new RedisCallback<Cursor<byte[]>>() {
            @Override
            public Cursor<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.sScan(key, options);
            }
        });
    }

    /**
     * Add {@code value} to a sorted set at {@code key}, or update its {@code score} if it already exists.
     * <p>
     * See http://redis.io/commands/zadd
     * 
     * @param key key
     * @param score score
     * @param value value
     * @return Boolean
     */
    public static Boolean zAdd(byte[] key, double score, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zAdd(key, score, value);
            }
        });
    }

    /**
     * Add {@code tuples} to a sorted set at {@code key}, or update its {@code score} if it already exists.
     * <p>
     * See http://redis.io/commands/zadd
     * 
     * @param key key
     * @param tuples tuples
     * @return Long
     */
    public static Long zAdd(byte[] key, Set<Tuple> tuples) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zAdd(key, tuples);
            }
        });
    }

    /**
     * Remove given {@code values} from set at {@code key} and return the number of removed elements.
     * <p>
     * See http://redis.io/commands/srem
     * 
     * @param key key
     * @param values values
     * @return Long
     */
    public static Long zRem(byte[] key, byte[]... values) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRem(key, values);
            }
        });
    }

    /**
     * Remove given {@code values} from set at {@code key} and return the number of removed elements.
     * <p>
     * See http://redis.io/commands/srem
     * 
     * @param key key
     * @param increment increment
     * @param value value
     * @return Double
     */
    public static Double zIncrBy(byte[] key, double increment, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zIncrBy(key, increment, value);
            }
        });
    }

    /**
     * Determine the index of element with {@code value} in a sorted set.
     * <p>
     * See http://redis.io/commands/zrank
     * 
     * @param key 
     * @param value value
     * @return Long
     */
    public static Long zRank(byte[] key, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRank(key, value);
            }
        });
    }

    /**
     * Determine the index of element with {@code value} in a sorted set when scored high to low.
     * <p>
     * See http://redis.io/commands/zrevrank
     * 
     * @param key key
     * @param value value
     * @return Long
     */
    public static Long zRevRank(byte[] key, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRank(key, value);
            }
        });
    }

    /**
     * Get elements between {@code begin} and {@code end} from sorted set.
     * <p>
     * See http://redis.io/commands/zrange
     * 
     * @param key key
     * @param begin begin
     * @param end end
     * @return Set<byte[]>
     */
    public static Set<byte[]> zRange(byte[] key, long begin, long end) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRange(key, begin, end);
            }
        });
    }

    /**
     * Get set of {@link Tuple}s between {@code begin} and {@code end} from sorted set.
     * <p>
     * See http://redis.io/commands/zrange
     * 
     * @param key key
     * @param begin begin
     * @param end end
     * @return Set<Tuple>
     */
    public static Set<Tuple> zRangeWithScores(byte[] key, long begin, long end) {
        return redisTemplate.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeWithScores(key, begin, end);
            }
        });
    }

    /**
     * Get elements where score is between {@code min} and {@code max} from sorted set.
     * <p>
     * See http://redis.io/commands/zrangebyscore
     * 
     * @param key key
     * @param min min
     * @param max max
     * @return Set<byte[]>
     */
    public static Set<byte[]> zRangeByScore(byte[] key, double min, double max) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, min, max);
            }
        });
    }

    /**
     * Get set of {@link Tuple}s where score is between {@code Range#min} and {@code Range#max} from sorted set.
     * 
     * @param key key
     * @param range range
     * @return Set<Tuple>
     * @since 1.6
     */
    public static Set<Tuple> zRangeByScoreWithScores(byte[] key, Range range) {
        return redisTemplate.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScoreWithScores(key, range);
            }
        });
    }

    /**
     * Get set of {@link Tuple}s where score is between {@code min} and {@code max} from sorted set.
     * <p>
     * See http://redis.io/commands/zrangebyscore
     * 
     * @param key key
     * @param min min
     * @param max max
     * @return Set<Tuple>
     */
    public static Set<Tuple> zRangeByScoreWithScores(byte[] key, double min, double max) {
        return redisTemplate.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScoreWithScores(key, min, max);
            }
        });
    }

    /**
     * Get elements in range from {@code begin} to {@code end} where score is between {@code min} and {@code max} from
     * sorted set.
     * <p>
     * See http://redis.io/commands/zrangebyscore
     * 
     * @param key key
     * @param min min
     * @param max max
     * @param offset offset
     * @param count count
     * @return Set<byte[]>
     */
    public static Set<byte[]> zRangeByScore(byte[] key, double min, double max, long offset, long count) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, min, max, offset, count);
            }
        });
    }

    /**
     * Get set of {@link Tuple}s in range from {@code begin} to {@code end} where score is between {@code min} and
     * {@code max} from sorted set.
     * <p>
     * See http://redis.io/commands/zrangebyscore
     * 
     * @param key key
     * @param min min
     * @param max max
     * @param offset offset
     * @param count count
     * @return Set<Tuple>
     */
    public static Set<Tuple> zRangeByScoreWithScores(byte[] key, double min, double max, long offset, long count) {
        return redisTemplate.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScoreWithScores(key, min, max, offset, count);
            }
        });
    }

    /**
     * Get set of {@link Tuple}s in range from {@code Limit#offset} to {@code Limit#offset + Limit#count} where score is
     * between {@code Range#min} and {@code Range#max} from sorted set.
     * 
     * @param key key
     * @param range range
     * @param limit limit
     * @return Set<Tuple>
     * @since 1.6
     */
    public static Set<Tuple> zRangeByScoreWithScores(byte[] key, Range range, Limit limit) {
        return redisTemplate.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScoreWithScores(key, range, limit);
            }
        });
    }

    /**
     * Get elements in range from {@code begin} to {@code end} from sorted set ordered from high to low.
     * <p>
     * See http://redis.io/commands/zrevrange
     * 
     * @param key key
     * @param begin begin
     * @param end end
     * @return Set<byte[]>
     */
    public static Set<byte[]> zRevRange(byte[] key, long begin, long end) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRange(key, begin, end);
            }
        });
    }

    /**
     * Get set of {@link Tuple}s in range from {@code begin} to {@code end} from sorted set ordered from high to low.
     * <p>
     * See http://redis.io/commands/zrevrange
     * 
     * @param key key
     * @param begin begin
     * @param end end
     * @return Set<Tuple>
     */
    public static Set<Tuple> zRevRangeWithScores(byte[] key, long begin, long end) {
        return redisTemplate.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeWithScores(key, begin, end);
            }
        });
    }

    /**
     * Get elements where score is between {@code min} and {@code max} from sorted set.
     * <p>
     * See http://redis.io/commands/zrangebyscore
     * 
     * @param key key
     * @param min min
     * @param max max
     * @return Set<byte[]>
     */
    public static Set<byte[]> zRevRangeByScore(byte[] key, double min, double max) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, min, max);
            }
        });
    }

    /**
     * Get elements where score is between {@code Range#min} and {@code Range#max} from sorted set ordered from high to
     * low.
     * 
     * @param key key
     * @param range range
     * @return Set<byte[]>
     * @since 1.6
     */
    public static Set<byte[]> zRevRangeByScore(byte[] key, Range range) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScore(key, range);
            }
        });
    }

    /**
     * Get set of {@link Tuple} where score is between {@code min} and {@code max} from sorted set ordered from high to
     * low.
     * <p>
     * See http://redis.io/commands/zrevrange
     * 
     * @param key key
     * @param min min
     * @param max max
     * @return Set<Tuple>
     */
    public static Set<Tuple> zRevRangeByScoreWithScores(byte[] key, double min, double max) {
        return redisTemplate.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScoreWithScores(key, min, max);
            }
        });
    }

    /**
     * Get elements in range from {@code begin} to {@code end} where score is between {@code min} and {@code max} from
     * sorted set ordered high -> low.
     * <p>
     * See http://redis.io/commands/zrevrangebyscore
     * 
     * @param key key
     * @param min min
     * @param max max
     * @param offset offset
     * @param count count
     * @return Set<byte[]>
     */
    public static Set<byte[]> zRevRangeByScore(byte[] key, double min, double max, long offset, long count) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScore(key, min, max, offset, count);
            }
        });
    }

    /**
     * Get elements in range from {@code Limit#offset} to {@code Limit#offset + Limit#count} where score is between
     * {@code Range#min} and {@code Range#max} from sorted set ordered high -> low.
     * 
     * @param key key
     * @param range range
     * @param limit limit
     * @return Set<byte[]>
     * @since 1.6
     */
    public static Set<byte[]> zRevRangeByScore(byte[] key, Range range, Limit limit) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScore(key, range, limit);
            }
        });
    }

    /**
     * Get set of {@link Tuple} in range from {@code begin} to {@code end} where score is between {@code min} and
     * {@code max} from sorted set ordered high -> low.
     * <p>
     * See http://redis.io/commands/zrevrangebyscore
     * 
     * @param key key
     * @param min min
     * @param max max
     * @param offset offset
     * @param count count
     * @return Set<Tuple>
     */
    public static Set<Tuple> zRevRangeByScoreWithScores(byte[] key, double min, double max, long offset, long count) {
        return redisTemplate.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScoreWithScores(key, min, max, offset, count);
            }
        });
    }

    /**
     * Get set of {@link Tuple} where score is between {@code Range#min} and {@code Range#max} from sorted set ordered
     * from high to low.
     * 
     * @param key key
     * @param range range
     * @return Set<Tuple>
     * @since 1.6
     */
    public static Set<Tuple> zRevRangeByScoreWithScores(byte[] key, Range range) {
        return redisTemplate.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScoreWithScores(key, range);
            }
        });
    }

    /**
     * Get set of {@link Tuple} in range from {@code Limit#offset} to {@code Limit#count} where score is between
     * {@code Range#min} and {@code Range#max} from sorted set ordered high -> low.
     * 
     * @param key key
     * @param range range
     * @param limit limit
     * @return Set<Tuple>
     * @since 1.6
     */
    public static Set<Tuple> zRevRangeByScoreWithScores(byte[] key, Range range, Limit limit) {
        return redisTemplate.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRevRangeByScoreWithScores(key, range, limit);
            }
        });
    }

    /**
     * Count number of elements within sorted set with scores between {@code min} and {@code max}.
     * <p>
     * See http://redis.io/commands/zcount
     * 
     * @param key key
     * @param min min
     * @param max max
     * @return Long
     */
    public static Long zCount(byte[] key, double min, double max) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zCount(key, min, max);
            }
        });
    }

    /**
     * Count number of elements within sorted set with scores between {@code Range#min} and {@code Range#max}.
     * 
     * @param key key
     * @param range range
     * @return Long
     * @since 1.6
     */
    public static Long zCount(byte[] key, Range range) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zCount(key, range);
            }
        });
    }

    /**
     * Get the size of sorted set with {@code key}.
     * <p>
     * See http://redis.io/commands/zcard
     * 
     * @param key key
     * @return Long
     */
    public static Long zCard(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zCard(key);
            }
        });
    }

    /**
     * Get the score of element with {@code value} from sorted set with key {@code key}.
     * <p>
     * See http://redis.io/commands/zrem
     * 
     * @param key key
     * @param value value
     * @return Double
     */
    public static Double zScore(byte[] key, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zScore(key, value);
            }
        });
    }

    /**
     * Remove elements in range between {@code begin} and {@code end} from sorted set with {@code key}.
     * <p>
     * See http://redis.io/commands/zremrange
     * 
     * @param key key
     * @param begin begin
     * @param end end
     * @return Long
     */
    public static Long zRemRange(byte[] key, long begin, long end) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRemRange(key, begin, end);
            }
        });
    }

    /**
     * Remove elements with scores between {@code min} and {@code max} from sorted set with {@code key}.
     * <p>
     * See http://redis.io/commands/zremrangebyscore
     * 
     * @param key key
     * @param min min
     * @param max max
     * @return Long
     */
    public static Long zRemRangeByScore(byte[] key, double min, double max) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRemRangeByScore(key, min, max);
            }
        });
    }

    /**
     * Remove elements with scores between {@code Range#min} and {@code Range#max} from sorted set with {@code key}.
     * 
     * @param key key
     * @param range range
     * @return Long
     * @since 1.6
     */
    public static Long zRemRangeByScore(byte[] key, Range range) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRemRangeByScore(key, range);
            }
        });
    }

    /**
     * Union sorted {@code sets} and store result in destination {@code key}.
     * <p>
     * See http://redis.io/commands/zunionstore
     * 
     * @param destKey destKey
     * @param sets sets
     * @return Long
     */
    public static Long zUnionStore(byte[] destKey, byte[]... sets) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zUnionStore(destKey, sets);
            }
        });
    }

    /**
     * Union sorted {@code sets} and store result in destination {@code key}.
     * <p>
     * See http://redis.io/commands/zunionstore
     * 
     * @param destKey destKey
     * @param aggregate aggregate
     * @param weights weights
     * @param sets sets
     * @return Long
     */
    public static Long zUnionStore(byte[] destKey, Aggregate aggregate, int[] weights, byte[]... sets) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zUnionStore(destKey, aggregate, weights, sets);
            }
        });
    }

    /**
     * Intersect sorted {@code sets} and store result in destination {@code key}.
     * <p>
     * See http://redis.io/commands/zinterstore
     * 
     * @param destKey destKey
     * @param sets sets
     * @return Long
     */
    public static Long zInterStore(byte[] destKey, byte[]... sets) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zInterStore(destKey, sets);
            }
        });
    }

    /**
     * Intersect sorted {@code sets} and store result in destination {@code key}.
     * <p>
     * See http://redis.io/commands/zinterstore
     * 
     * @param destKey destKey
     * @param aggregate aggregate
     * @param weights weights
     * @param sets sets
     * @return Long
     */
    public static Long zInterStore(byte[] destKey, Aggregate aggregate, int[] weights, byte[]... sets) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zInterStore(destKey, aggregate, weights, sets);
            }
        });
    }

    /**
     * Use a {@link Cursor} to iterate over elements in sorted set at {@code key}.
     * <p>
     * See http://redis.io/commands/scan
     * 
     * @since 1.4
     * @param key key
     * @param options options
     * @return Cursor<Tuple>
     */
    public static Cursor<Tuple> zScan(byte[] key, ScanOptions options) {
        return redisTemplate.execute(new RedisCallback<Cursor<Tuple>>() {
            @Override
            public Cursor<Tuple> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zScan(key, options);
            }
        });
    }

    /**
     * Get elements where score is between {@code min} and {@code max} from sorted set.
     * <p>
     * See http://redis.io/commands/zrangebyscore
     * 
     * @since 1.5
     * @param key key
     * @param min min
     * @param max max
     * @return Set<byte[]>
     */
    public static Set<byte[]> zRangeByScore(byte[] key, String min, String max) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, min, max);
            }
        });
    }

    /**
     * Get elements where score is between {@code Range#min} and {@code Range#max} from sorted set.
     * 
     * @param key key
     * @param range range
     * @return Set<byte[]>
     * @since 1.6
     */
    public static Set<byte[]> zRangeByScore(byte[] key, Range range) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, range);
            }
        });
    }

    /**
     * Get elements in range from {@code begin} to {@code end} where score is between {@code min} and {@code max} from
     * sorted set.
     * <p>
     * See http://redis.io/commands/zrangebyscore
     * 
     * @since 1.5
     * @param key key
     * @param min min
     * @param max max
     * @param offset offset
     * @param count count
     * @return Set<byte[]>
     */
    public static Set<byte[]> zRangeByScore(byte[] key, String min, String max, long offset, long count) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, min, max, offset, count);
            }
        });
    }

    /**
     * Get elements in range from {@code Limit#count} to {@code Limit#offset} where score is between {@code Range#min} and
     * {@code Range#max} from sorted set.
     * 
     * @param key key
     * @param range range
     * @param limit limit
     * @return Set<byte[]>
     * @since 1.6
     */
    public static Set<byte[]> zRangeByScore(byte[] key, Range range, Limit limit) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByScore(key, range, limit);
            }
        });
    }

    /**
     * Get all the elements in the sorted set at {@literal key} in lexicographical ordering.
     * 
     * @param key must not be {@literal null}.
     * @return Set<byte[]
     * @since 1.6
     */
    public static Set<byte[]> zRangeByLex(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByLex(key);
            }
        });
    }

    /**
     * Get all the elements in {@link Range} from the sorted set at {@literal key} in lexicographical ordering.
     * 
     * @param key must not be {@literal null}.
     * @param range must not be {@literal null}.
     * @return Set<byte[]>
     * @since 1.6
     */
    public static Set<byte[]> zRangeByLex(byte[] key, Range range) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByLex(key, range);
            }
        });
    }

    /**
     * Get all the elements in {@link Range} from the sorted set at {@literal key} in lexicographical ordering. Result is
     * limited via {@link Limit}.
     * 
     * @param key must not be {@literal null}.
     * @param range must not be {@literal null}.
     * @param limit can be {@literal null}.
     * @return Set<byte[]>
     * @since 1.6
     */
    public static Set<byte[]> zRangeByLex(byte[] key, Range range, Limit limit) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.zRangeByLex(key, range, limit);
            }
        });
    }

    /**
     * Set the {@code value} of a hash {@code field}.
     * 
     * @see http://redis.io/commands/hset
     * @param key key
     * @param field field
     * @param value value
     * @return Boolean
     */
    public static Boolean hSet(byte[] key, byte[] field, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hSet(key, field, value);
            }
        });
    }

    /**
     * Set the {@code value} of a hash {@code field} only if {@code field} does not exist.
     * 
     * @see http://redis.io/commands/hsetnx
     * @param key key
     * @param field field
     * @param value value
     * @return Boolean
     */
    public static Boolean hSetNX(byte[] key, byte[] field, byte[] value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hSetNX(key, field, value);
            }
        });
    }

    /**
     * Get value for given {@code field} from hash at {@code key}.
     * 
     * @see http://redis.io/commands/hget
     * @param key key
     * @param field field
     * @return byte[
     */
    public static byte[] hGet(byte[] key, byte[] field) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hGet(key, field);
            }
        });
    }

    /**
     * Get values for given {@code fields} from hash at {@code key}.
     * 
     * @see http://redis.io/commands/hmget
     * @param key key
     * @param fields fields
     * @return List<byte[]>
     */
    public static List<byte[]> hMGet(byte[] key, byte[]... fields) {
        return redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hMGet(key, fields);
            }
        });
    }

    /**
     * Set multiple hash fields to multiple values using data provided in {@code hashes}
     * 
     * @see http://redis.io/commands/hmset
     * @param key key
     * @param hashes hashes
     */
    public static void hMSet(byte[] key, Map<byte[], byte[]> hashes) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.hMSet(key, hashes);
                return null;
            }
        });
    }

    /**
     * Increment {@code value} of a hash {@code field} by the given {@code delta}.
     * 
     * @see http://redis.io/commands/hincrby
     * @param key key
     * @param field field
     * @param delta delta
     * @return Long
     */
    public static Long hIncrBy(byte[] key, byte[] field, long delta) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hIncrBy(key, field, delta);
            }
        });
    }

    /**
     * Increment {@code value} of a hash {@code field} by the given {@code delta}.
     * 
     * @see http://redis.io/commands/hincrbyfloat
     * @param key key
     * @param field field
     * @param delta delta
     * @return Double
     */
    public static Double hIncrBy(byte[] key, byte[] field, double delta) {
        return redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hIncrBy(key, field, delta);
            }
        });
    }

    /**
     * Determine if given hash {@code field} exists.
     * 
     * @see http://redis.io/commands/hexits
     * @param key key
     * @param field field
     * @return Boolean
     */
    public static Boolean hExists(byte[] key, byte[] field) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hExists(key, field);
            }
        });
    }

    /**
     * Delete given hash {@code fields}.
     * 
     * @see http://redis.io/commands/hdel
     * @param key key
     * @param fields fields
     * @return Long
     */
    public static Long hDel(byte[] key, byte[]... fields) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hDel(key, fields);
            }
        });
    }

    /**
     * Get size of hash at {@code key}.
     * 
     * @see http://redis.io/commands/hlen
     * @param key key
     * @return Long
     */
    public static Long hLen(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hLen(key);
            }
        });
    }

    /**
     * Get key set (fields) of hash at {@code key}.
     * 
     * @see http://redis.io/commands/h?
     * @param key key
     * @return Set<byte[]>
     */
    public static Set<byte[]> hKeys(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hKeys(key);
            }
        });
    }

    /**
     * Get entry set (values) of hash at {@code field}.
     * 
     * @see http://redis.io/commands/hvals
     * @param key key
     * @return List<byte[]>
     */
    public static List<byte[]> hVals(byte[] key) {
        return redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hVals(key);
            }
        });
    }

    /**
     * Get entire hash stored at {@code key}.
     * 
     * @see http://redis.io/commands/hgetall
     * @param key key
     * @return Map
     */
    public static Map<byte[], byte[]> hGetAll(byte[] key) {
        return redisTemplate.execute(new RedisCallback<Map<byte[], byte[]>>() {
            @Override
            public Map<byte[], byte[]> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hGetAll(key);
            }
        });
    }

    /**
     * Use a {@link Cursor} to iterate over entries in hash at {@code key}.
     * 
     * @since 1.4
     * @see http://redis.io/commands/scan
     * @param key key
     * @param options options
     * @return Cursor
     */
    public static Cursor<Entry<byte[], byte[]>> hScan(byte[] key, ScanOptions options) {
        return redisTemplate.execute(new RedisCallback<Cursor<Entry<byte[], byte[]>>>() {
            @Override
            public Cursor<Entry<byte[], byte[]>> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.hScan(key, options);
            }
        });
    }

    /**
     * Mark the start of a transaction block. <br>
     * Commands will be queued and can then be executed by calling {@link #exec()} or rolled back using {@link #discard()}
     * <p>
     * See http://redis.io/commands/multi
     */
    public static void multi() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.multi();
                return null;
            }
        });
    }

    /**
     * Executes all queued commands in a transaction started with {@link #multi()}. <br>
     * If used along with {@link #watch(byte[])} the operation will fail if any of watched keys has been modified.
     * <p>
     * See http://redis.io/commands/exec
     * 
     * @return List of replies for each executed command.
     */
    public static List<Object> exec() {
        return redisTemplate.execute(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.exec();
            }
        });
    }

    /**
     * Discard all commands issued after {@link #multi()}.
     * <p>
     * See http://redis.io/commands/discard
     */
    public static void discard() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.discard();
                return null;
            }
        });
    }

    /**
     * Watch given {@code keys} for modifications during transaction started with {@link #multi()}.
     * <p>
     * See http://redis.io/commands/watch
     * 
     * @param keys keys
     */
    public static void watch(byte[]... keys) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.watch(keys);
                return null;
            }
        });
    }

    /**
     * Flushes all the previously {@link #watch(byte[])} keys.
     * <p>
     * See http://redis.io/commands/unwatch
     */
    public static void unwatch() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.unwatch();
                return null;
            }
        });
    }

    /**
     * Indicates whether the current connection is subscribed (to at least one channel) or not.
     * 
     * @return true if the connection is subscribed, false otherwise
     */
    public static boolean isSubscribed() {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.isSubscribed();
            }
        });
    }

    /**
     * Returns the current subscription for this connection or null if the connection is not subscribed.
     * 
     * @return the current subscription, null if none is available
     */
    public static Subscription getSubscription() {
        return redisTemplate.execute(new RedisCallback<Subscription>() {
            @Override
            public Subscription doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getSubscription();
            }
        });
    }

    /**
     * Publishes the given message to the given channel.
     * 
     * @param channel the channel to publish to
     * @param message message to publish
     * @return the number of clients that received the message
     */
    public static Long publish(byte[] channel, byte[] message) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.publish(channel, message);
            }
        });
    }

    /**
     * Subscribes the connection to the given channels. Once subscribed, a connection enters listening mode and can only
     * subscribe to other channels or unsubscribe. No other commands are accepted until the connection is unsubscribed.
     * <p>
     * Note that this operation is blocking and the current thread starts waiting for new messages immediately.
     * 
     * @param listener message listener
     * @param channels channel names
     */
    public static void subscribe(MessageListener listener, byte[]... channels) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.subscribe(listener, channels);
                return null;
            }
        });
    }

    /**
     * Subscribes the connection to all channels matching the given patterns. Once subscribed, a connection enters
     * listening mode and can only subscribe to other channels or unsubscribe. No other commands are accepted until the
     * connection is unsubscribed.
     * <p>
     * Note that this operation is blocking and the current thread starts waiting for new messages immediately.
     * 
     * @param listener message listener
     * @param patterns channel name patterns
     */
    public static void pSubscribe(MessageListener listener, byte[]... patterns) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.pSubscribe(listener, patterns);
                return null;
            }
        });
    }

    /**
     * Select the DB with given positive {@code dbIndex}.
     * <p>
     * See http://redis.io/commands/select
     * 
     * @param dbIndex dbIndex
     */
    public static void select(int dbIndex) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.select(dbIndex);
                return null;
            }
        });
    }

    /**
     * Returns {@code message} via server roundtrip.
     * <p>
     * See http://redis.io/commands/echo
     * 
     * @param message message
     * @return byte[]
     */
    public static byte[] echo(byte[] message) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.echo(message);
            }
        });
    }

    /**
     * Test connection.
     * <p>
     * See http://redis.io/commands/ping
     * 
     * @return Server response message - usually {@literal PONG}.
     */
    public static String ping() {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.ping();
            }
        });
    }

    /**
     * Start an {@literal Append Only File} rewrite process on server.
     * <p>
     * See http://redis.io/commands/bgrewriteaof
     * 
     * @deprecated As of 1.3, use {@link #bgReWriteAof}.
     */
    @Deprecated
    public static void bgWriteAof() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.bgWriteAof();
                return null;
            }
        });
    }

    /**
     * Start an {@literal Append Only File} rewrite process on server.
     * <p>
     * See http://redis.io/commands/bgrewriteaof
     * 
     * @since 1.3
     */
    public static void bgReWriteAof() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.bgReWriteAof();
                return null;
            }
        });
    }

    /**
     * Start background saving of db on server.
     * <p>
     * See http://redis.io/commands/bgsave
     */
    public static void bgSave() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.bgSave();
                return null;
            }
        });
    }

    /**
     * Get time of last {@link #bgSave()} operation in seconds.
     * <p>
     * See http://redis.io/commands/lastsave
     * 
     * @return Long
     */
    public static Long lastSave() {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.lastSave();
            }
        });
    }

    /**
     * Synchronous save current db snapshot on server.
     * <p>
     * See http://redis.io/commands/save
     */
    public static void save() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.save();
                return null;
            }
        });
    }

    /**
     * Get the total number of available keys in currently selected database.
     * <p>
     * See http://redis.io/commands/dbsize
     * 
     * @return Long
     */
    public static Long dbSize() {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.dbSize();
            }
        });
    }

    /**
     * Delete all keys of the currently selected database.
     * <p>
     * See http://redis.io/commands/flushdb
     */
    public static void flushDb() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.flushDb();
                return null;
            }
        });
    }

    /**
     * Delete all <b>all keys</b> from <b>all databases</b>.
     * <p>
     * See http://redis.io/commands/flushall
     */
    public static void flushAll() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.flushAll();
                return null;
            }
        });
    }

    /**
     * Load {@literal default} server information like
     * <ul>
     * <li>mempory</li>
     * <li>cpu utilization</li>
     * <li>replication</li>
     * </ul>
     * <p>
     * See http://redis.io/commands/info
     * 
     * @return Properties
     */
    public static Properties info() {
        return redisTemplate.execute(new RedisCallback<Properties>() {
            @Override
            public Properties doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.info();
            }
        });
    }

    /**
     * Load server information for given {@code selection}.
     * <p>
     * See http://redis.io/commands/info
     * 
     * @param section section
     * @return Properties
     */
    public static Properties info(String section) {
        return redisTemplate.execute(new RedisCallback<Properties>() {
            @Override
            public Properties doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.info(section);
            }
        });
    }

    /**
     * Shutdown server.
     * <p>
     * See http://redis.io/commands/shutdown
     */
    public static void shutdown() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.shutdown();
                return null;
            }
        });
    }

    /**
     * Shutdown server.
     * <p>
     * See http://redis.io/commands/shutdown
     * 
     * @param option option
     * @since 1.3
     */
    public static void shutdown(ShutdownOption option) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.shutdown(option);
                return null;
            }
        });
    }

    /**
     * Load configuration parameters for given {@code pattern} from server.
     * <p>
     * See http://redis.io/commands/config-get
     * 
     * @param pattern pattern
     * @return List<String>
     */
    public static List<String> getConfig(String pattern) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getConfig(pattern);
            }
        });
    }

    /**
     * Set server configuration for {@code param} to {@code value}.
     * <p>
     * See http://redis.io/commands/config-set
     * 
     * @param param param
     * @param value value
     */
    public static void setConfig(String param, String value) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.setConfig(param, value);
                return null;
            }
        });
    }

    /**
     * Reset statistic counters on server. <br>
     * Counters can be retrieved using {@link #info()}.
     * <p>
     * See http://redis.io/commands/config-resetstat
     */
    public static void resetConfigStats() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.resetConfigStats();
                return null;
            }
        });
    }

    /**
     * Request server timestamp using {@code TIME} command.
     * 
     * @return current server time in milliseconds.
     * @since 1.1
     */
    public static Long time() {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.time();
            }
        });
    }

    /**
     * Closes a given client connection identified by {@literal host:port}.
     * 
     * @param host of connection to close.
     * @param port of connection to close
     * @since 1.3
     */
    public static void killClient(String host, int port) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.killClient(host, port);
                return null;
            }
        });
    }

    /**
     * Assign given name to current connection.
     * 
     * @param name name
     * @since 1.3
     */
    public static void setClientName(byte[] name) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.setClientName(name);
                return null;
            }
        });
    }

    /**
     * Returns the name of the current connection.
     * <p>
     * See http://redis.io/commands/client-getname
     * 
     * @return String
     * @since 1.3
     */
    public static String getClientName() {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getClientName();
            }
        });
    }

    /**
     * Request information and statistics about connected clients.
     * <p>
     * See http://redis.io/commands/client-list
     * 
     * @return {@link List} of {@link RedisClientInfo} objects.
     * @since 1.3
     */
    public static List<RedisClientInfo> getClientList() {
        return redisTemplate.execute(new RedisCallback<List<RedisClientInfo>>() {
            @Override
            public List<RedisClientInfo> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.getClientList();
            }
        });
    }

    /**
     * Change redis replication setting to new master.
     * <p>
     * See http://redis.io/commands/slaveof
     * 
     * @param host host
     * @param port port
     * @since 1.3
     */
    public static void slaveOf(String host, int port) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.slaveOf(host, port);
                return null;
            }
        });
    }

    /**
     * Change server into master.
     * <p>
     * See http://redis.io/commands/slaveof
     * 
     * @since 1.3
     */
    public static void slaveOfNoOne() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.slaveOfNoOne();
                return null;
            }
        });
    }

    /**
     * @param key must not be {@literal null}.
     * @param target must not be {@literal null}.
     * @param dbIndex dbIndex
     * @param option can be {@literal null}. Defaulted to {@link MigrateOption#COPY}.
     * @since 1.7
     */
    public static void migrate(byte[] key, RedisNode target, int dbIndex, MigrateOption option) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.migrate(key, target, dbIndex, option);
                return null;
            }
        });
    }

    /**
     * @param key must not be {@literal null}.
     * @param target must not be {@literal null}.
     * @param dbIndex dbIndex
     * @param option can be {@literal null}. Defaulted to {@link MigrateOption#COPY}.
     * @param timeout timeout
     * @since 1.7
     */
    public static void migrate(byte[] key, RedisNode target, int dbIndex, MigrateOption option, long timeout) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.migrate(key, target, dbIndex, option, timeout);
                return null;
            }
        });
    }

    /**
     * Flush lua script cache.
     * <p>
     * See http://redis.io/commands/script-flush
     */
    public static void scriptFlush() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.scriptFlush();
                return null;
            }
        });
    }

    /**
     * Kill current lua script execution.
     * <p>
     * See http://redis.io/commands/script-kill
     */
    public static void scriptKill() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.scriptKill();
                return null;
            }
        });
    }

    /**
     * Load lua script into scripts cache, without executing it.<br>
     * Execute the script by calling {@link #evalSha(String, ReturnType, int, byte[])}.
     * <p>
     * See http://redis.io/commands/script-load
     * 
     * @param script script
     * @return String
     */
    public static String scriptLoad(byte[] script) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.scriptLoad(script);
            }
        });
    }

    /**
     * Check if given {@code scriptShas} exist in script cache.
     * <p>
     * See http://redis.io/commands/script-exits
     * 
     * @param scriptShas scriptShas
     * @return one entry per given scriptSha in returned list.
     */
    public static List<Boolean> scriptExists(String... scriptShas) {
        return redisTemplate.execute(new RedisCallback<List<Boolean>>() {
            @Override
            public List<Boolean> doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.scriptExists(scriptShas);
            }
        });
    }

    /**
     * Evaluate given {@code script}.
     * <p>
     * See http://redis.io/commands/eval
     * 
     * @param <T> t
     * @param script script
     * @param returnType returnType
     * @param numKeys numKeys
     * @param keysAndArgs keysAndArgs
     * @return T
     */
    public static <T> T eval(byte[] script, ReturnType returnType, int numKeys, byte[]... keysAndArgs) {
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.eval(script, returnType, numKeys, keysAndArgs);
            }
        });
    }

    /**
     * Evaluate given {@code scriptSha}.
     * <p>
     * See http://redis.io/commands/evalsha
     * 
     * @param <T> t
     * @param scriptSha scriptSha
     * @param returnType returnType
     * @param numKeys numKeys
     * @param keysAndArgs keysAndArgs
     * @return T
     * @since 1.5
     */
    public static <T> T evalSha(String scriptSha, ReturnType returnType, int numKeys, byte[]... keysAndArgs) {
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.evalSha(scriptSha, returnType, numKeys, keysAndArgs);
            }
        });
    }

    /**
     * Evaluate given {@code scriptSha}.
     * <p>
     * See http://redis.io/commands/evalsha
     * 
     * @param <T> t
     * @param scriptSha scriptSha
     * @param returnType returnType
     * @param numKeys numKeys
     * @param keysAndArgs keysAndArgs
     * @return T
     * @since 1.5
     */
    public static <T> T evalSha(byte[] scriptSha, ReturnType returnType, int numKeys, byte[]... keysAndArgs) {
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.evalSha(scriptSha, returnType, numKeys, keysAndArgs);
            }
        });
    }

    /**
     * Adds given {@literal values} to the HyperLogLog stored at given {@literal key}.
     * 
     * @param key key
     * @param values values
     * @return Long
     */
    public static Long pfAdd(byte[] key, byte[]... values) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.pfAdd(key, values);
            }
        });
    }

    /**
     * Return the approximated cardinality of the structures observed by the HyperLogLog at {@literal key(s)}.
     * 
     * @param keys keys
     * @return Long
     */
    public static Long pfCount(byte[]... keys) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redis) throws DataAccessException {
                return redis.pfCount(keys);
            }
        });
    }

    /**
     * Merge N different HyperLogLogs at {@literal sourceKeys} into a single {@literal destinationKey}.
     * 
     * @param destinationKey destinationKey
     * @param sourceKeys sourceKeys
     */
    public static void pfMerge(byte[] destinationKey, byte[]... sourceKeys) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection redis) throws DataAccessException {
                redis.pfMerge(destinationKey, sourceKeys);
                return null;
            }
        });
    }
}
