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
package com.zxy.commons.mq.serialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.producer.Partitioner;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

/** 
 * Abstract serialize
 * 
 * <p>
 * Deprecated
 * <p>
 * <a href="AbstractSerialize.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @param <K> key
 * @since 1.0 
*/
@Deprecated
public abstract class AbstractSerialize<K> implements Encoder<K>, Partitioner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @SuppressWarnings({ "PMD.UncommentedEmptyConstructor", "PMD.UnusedFormalParameter" })
    public AbstractSerialize(VerifiableProperties properties) {
    }

    /**
     * 如果需要作为路由key对象的话，需要实现，否则不用实现
     * 
     * @param router router
     * @return hashcode
    */
    protected abstract int routerKeyHashCode(K router);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int partition(Object router, int numPartitions) {
        int partition = 0;
        if (router != null) {
            @SuppressWarnings("unchecked")
            int iTemp = routerKeyHashCode((K)router);
            if (iTemp == Integer.MIN_VALUE) {
                partition = -1 * iTemp % numPartitions;
            } else {
                partition = Math.abs(iTemp) % numPartitions;
            }
        }
        logger.debug("router:{}, numPartitions:{}, partition:{}", router, numPartitions, partition);
        return partition;
    }
}
