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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;

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
//@Configuration
//@EnableWebMvc
@EnableSwagger2
//@Profile("dev")
//@PropertySource(value = "classpath:apidocs.properties", ignoreResourceNotFound = true)
public class ApplicationSwaggerConfig {
//  @Value("${apidocs.groupName}")
//  private String groupName;
  
  @Value("${apidocs.host}")
  private String host;
  
  @Value("${apidocs.port}")
  private String port;
  
  @Value("${apidocs.basepath}")
  private String basePath;
  
//  @Value("${apidocs.enabled}")
//  private boolean enabled;
  
  @Value("${apidocs.title}")
  private String title;

  @Value("${apidocs.description}")
  private String description;

  @Value("${apidocs.termsOfServiceUrl}")
  private String termsOfServiceUrl;

  @Value("${apidocs.contact.name}")
  private String contactName;

  @Value("${apidocs.contact.email}")
  private String contactEmail;

  @Value("${apidocs.contact.url}")
  private String contactUrl;

  @Value("${apidocs.version}")
  private String version;
  
  /** 排除的url，多个用逗号分隔 */
  @Value("${apidocs.excludes}")
  private String excludes;
  
  /*//To resolve ${} in @Value
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
      return new PropertySourcesPlaceholderConfigurer();
  }*/
  
  @Bean
  public Docket appApi() {
      Docket docket = new Docket(DocumentationType.SWAGGER_2);
//      if(groupName!=null && groupName.length() > 0) {
//          docket.groupName(groupName);
//      }
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
//        .enable(enabled)
        .forCodeGeneration(true)
        .select();
      Predicate<String> paths = appPaths();
      if(paths != null) {
          builder.paths(paths);
      }
      return builder.build();
  }

//  @Bean
//  public Docket appAllApi() {
//    return new Docket(DocumentationType.SWAGGER_2)
//        .groupName("all")
//        .apiInfo(apiInfo())
////        .enable(false)
//        .forCodeGeneration(true)
//        .select()
////        .paths(appPaths())
//        .build();
//  }
  
  private Predicate<String> appPaths() {
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
//  private Predicate<String> userOnlyEndpoints() {
//      return new Predicate<String>() {
//          @Override
//          public boolean apply(String input) {
//              return input.contains("user");
//          }
//      };
//  }
//
  private ApiInfo apiInfo() {
      ApiInfoBuilder builder = new ApiInfoBuilder();
      builder.title(title)
        .description(description)
        .termsOfServiceUrl(termsOfServiceUrl)
        .contact(new Contact(contactName, contactUrl, contactEmail))
//        .license("Apache License Version 2.0")
//        .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
        .version(version);
        return builder.build();
  }

//  @Bean
//  public Docket configSpringfoxDocket_all() {
//    return new Docket(DocumentationType.SWAGGER_2)
//        .produces(Sets.newHashSet("application/json"))
//        .consumes(Sets.newHashSet("application/json"))
//        .protocols(Sets.newHashSet("http", "https"))
//        .forCodeGeneration(true)
//        .apiInfo(apiInfo())
//        .select()
////        .paths(regex(".*"))
//        .build();
//  }

//  public void afterPropertiesSet() throws Exception {
//
//  }

}
