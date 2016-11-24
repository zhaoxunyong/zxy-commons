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

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

/**
 *  An {@link Converter} model mapper configurer. This template method class allows to specify
 * the {@link Converter} that will be registered within the model mapper.
 *
 * <p>
 * <a href="ConverterConfigurerSupport.java"><i>View Source</i></a>
 * 
 * @param <S> the source object type
 * @param <D> the destination object type
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("PMD.AbstractNaming")
public abstract class ConverterConfigurerSupport<S, D> implements ModelMapperConfigurer {

    /**
     * Allows to specify a custom converter between two types.
     *
     * @return the converter
     */
    protected abstract Converter<S,D> converter();

    /**
     * Configures the passed {@link ModelMapper} instance by registering the {@link Converter} defined by
     * {@link #converter()} method.
     *
     * @param modelMapper {@link ModelMapper} instance to be configured
     */
    @Override
    public void configure(ModelMapper modelMapper) {

        modelMapper.addConverter(converter());
    }
}
