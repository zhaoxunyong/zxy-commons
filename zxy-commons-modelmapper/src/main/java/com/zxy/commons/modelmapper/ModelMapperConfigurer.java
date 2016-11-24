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

/**
 * Allows to set specific behaviour of the {@link ModelMapper} instance. The concrete implementation of this interface
 * needs to be registered within the application context in order to be processed.
 *
 * <pre><strong>Example:</strong>
 * &#064;Component
 * public class UserMapping implements ModelMapperConfigurer {
 *
 *      void configure(ModelMapper modelMapper) {
 *          modelMapper.getConfiguration()
 *              .setSourceNamingConvention(NamingConventions.NONE);
 *              .setDestinationNamingConvention(NamingConventions.NONE);
 *      }
 * }
 * </pre>
 * <p>
 * <a href="ModelMapperConfigurer.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public interface ModelMapperConfigurer {

    /**
     * Configures the instance of {@link org.modelmapper.ModelMapper}.
     *
     * @param modelMapper {@link ModelMapper} instance to be configured
     */
    void configure(ModelMapper modelMapper);
}
