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
package com.zxy.plugins.aggregate;

import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.api.plugins.*

import org.gradle.api.tasks.javadoc.Javadoc

/**
 * 
 * Aggregate all subproject javadocs
 * see: https://github.com/nebula-plugins/gradle-aggregate-javadocs-plugin
 * 
 * <p>
 * 需要在build.gradle中定义以下task：
 * <pre>
 * task aggregateJavadocs(type: com.zxy.plugins.aggregate.AggregateJavadocs) {
 *    destinationDir rootProject.file("$rootProject.buildDir/docs/javadoc")
 *    failOnErrors = false
 * }
 * </pre>
 * <p>
 * <a href="AggregateJavadocs.groovy"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
class AggregateJavadocs extends Javadoc {
    @Input boolean failOnErrors
    
    /**
     * 初始化默认参数
     */
    AggregateJavadocs() {
        Project rootProject = project.rootProject
        Set<Project> javaSubprojects = getJavaSubprojects(rootProject)
        description = 'Aggregates Javadoc API documentation of all subprojects.'
        group = JavaBasePlugin.DOCUMENTATION_GROUP
//        dependsOn javaSubprojects.javadoc

        source javaSubprojects.javadoc.source
        destinationDir rootProject.file("$rootProject.buildDir/docs/javadoc")
        classpath = rootProject.files(javaSubprojects.javadoc.classpath)
        options {
            locale = 'zh_CN'
            encoding = 'UTF-8'
            docFilesSubDirs = true
            failOnError = this.failOnErrors
            excludeDocFilesSubDir '.git'
        }
    }
    
    @TaskAction
    void start() {
        logger.quiet "Aggregate ${project.name} javadoc finished!"
    }
    
    private Set<Project> getJavaSubprojects(Project rootProject) {
        rootProject.subprojects.findAll { subproject -> subproject.plugins.hasPlugin(JavaPlugin) }
    }
    
}


// 在build.gradle文件中用以下代码实现效果一样
//    def javaSubprojects = rootProject.subprojects.findAll {
//        subproject -> subproject.plugins.hasPlugin(JavaPlugin)
//    }
//
//    task aggregateJavadocs(type: Javadoc) {
//        description = 'Aggregates Javadoc API documentation of all subprojects.'
//        group = JavaBasePlugin.DOCUMENTATION_GROUP
//        dependsOn javaSubprojects.javadoc
//
//        source javaSubprojects.javadoc.source
//        destinationDir rootProject.file("$rootProject.buildDir/docs/javadoc")
//        classpath = rootProject.files(javaSubprojects.javadoc.classpath)
//        options {
//            locale = 'zh_CN'
//            encoding = 'UTF-8'
//            docFilesSubDirs = true
//            failOnError = false
//            excludeDocFilesSubDir '.git'
//        }
//    }
