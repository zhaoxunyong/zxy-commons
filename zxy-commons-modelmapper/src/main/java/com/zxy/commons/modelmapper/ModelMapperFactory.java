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
package com.zxy.commons.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;

/**
 * ModelMapper factory
 * 
 * <p>
 * 注意：此类为非spring方式生成的ModelMapper对象。
 * <p>
 * <a href="ModelMapperFactory.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public class ModelMapperFactory {
    private final ModelMapper modelMapper;
    private final static class ModelMapperFactoryBuilder {
        private final static ModelMapperFactory INSTANCE = new ModelMapperFactory();
    }

    private ModelMapperFactory() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSourceNamingConvention(NamingConventions.NONE)
                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setDestinationNamingConvention(NamingConventions.NONE);
    }
    
    /**
     * @return the modelMapper
     */
    public ModelMapper getModelMapper() {
        return modelMapper;
    }



    /**
     * 获取ModelMapper实例
     * 
     * @param propertyMaps propertyMaps
     * @return ModelMapper实例
     */
    @SafeVarargs
    public final static ModelMapper getModelMapper(PropertyMap<?, ?>... propertyMaps) {
        ModelMapper modelMapper = ModelMapperFactoryBuilder.INSTANCE.getModelMapper();
        if(propertyMaps!=null && propertyMaps.length > 0) {
            for(PropertyMap<?, ?> propertyMap : propertyMaps) {
                modelMapper.addMappings(propertyMap);
            }
        }
        return modelMapper;
    }
}
