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
package com.zxy.commons.apidocs.conf;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Application swagger config
 * 
 * <p>
 * <a href="ApplicationSwaggerConfig.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
@Profile("swagger")
@PropertySource(value = "classpath:apidocs.properties", ignoreResourceNotFound = true)
@Conditional(SwaggerConfiguareCondition.class)
public class ApplicationSwaggerConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket appApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        //        if(groupName!=null && groupName.length() > 0) {
        //            docket.groupName(groupName);
        //        }
        String host = env.getProperty("apidocs.host", "");
        String port = env.getProperty("apidocs.port", "");
        String basePath = env.getProperty("apidocs.basepath", "");
        if(StringUtils.isEmpty(host)) {
            try {
                host = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                host = "localhost";
            }
        }
        if(!StringUtils.isEmpty(port)) {
            docket.host(host+":"+port);
        } else {
            docket.host(host);
        }
        if(!StringUtils.isEmpty(basePath)) {
            docket.pathProvider(new AbstractPathProvider() {

                @Override
                protected String getDocumentationPath() {
                    return "/";
                }

                @Override
                protected String applicationPath() {
                    return basePath;
                }
            });
        }
        ApiSelectorBuilder builder = docket.apiInfo(apiInfo())
                //          .enable(enabled)
                .forCodeGeneration(true)
                .select();
        Predicate<String> paths = appPaths();
        if(paths != null) {
            builder.paths(paths);
        }
        return builder.build();
    }

    //    @Bean
    //    public Docket appAllApi() {
    //      return new Docket(DocumentationType.SWAGGER_2)
    //          .groupName("all")
    //          .apiInfo(apiInfo())
    ////          .enable(false)
    //          .forCodeGeneration(true)
    //          .select()
    ////          .paths(appPaths())
    //          .build();
    //    }

    private Predicate<String> appPaths() {
        String excludes = env.getProperty("apidocs.excludes", "");
        List<Predicate<String>> predicates = new ArrayList<>();
        if(excludes!=null && excludes.length() > 0) {
            List<String> excludeList = Splitter.on(",").trimResults().splitToList(excludes);
            for(String exclude : excludeList) {
                predicates.add(regex(exclude));
            }
            return or(predicates);
        }
        return null;
    }
    //
    //    private Predicate<String> userOnlyEndpoints() {
    //        return new Predicate<String>() {
    //            @Override
    //            public boolean apply(String input) {
    //                return input.contains("user");
    //            }
    //        };
    //    }
    //
    private ApiInfo apiInfo() {
        String title = env.getProperty("apidocs.title", "");
        String description = env.getProperty("apidocs.description", "");
        String termsOfServiceUrl = env.getProperty("apidocs.termsOfServiceUrl", "");
        String contactName = env.getProperty("apidocs.contact.name", "");
        String contactEmail = env.getProperty("apidocs.contact.email", "");
        String contactUrl = env.getProperty("apidocs.contact.url", "");
        String version = env.getProperty("apidocs.version", "");
        ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                //          .license("Apache License Version 2.0")
                //          .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version(version);
        return builder.build();
    }

    //    @Bean
    //    public Docket configSpringfoxDocket_all() {
    //      return new Docket(DocumentationType.SWAGGER_2)
    //          .produces(Sets.newHashSet("application/json"))
    //          .consumes(Sets.newHashSet("application/json"))
    //          .protocols(Sets.newHashSet("http", "https"))
    //          .forCodeGeneration(true)
    //          .apiInfo(apiInfo())
    //          .select()
    ////          .paths(regex(".*"))
    //          .build();
    //    }

    //    public void afterPropertiesSet() throws Exception {
    //
    //    }

}