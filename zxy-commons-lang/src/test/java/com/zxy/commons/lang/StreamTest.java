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
package com.zxy.commons.lang;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.Lists;

import rx.Observable;

/** 
 * Stream test
 * 
 * <p>
 * <a href="StreamTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
@SuppressWarnings("PMD")
public class StreamTest {
    
    @Test
    public void toList() throws IOException {
        List<Object1> lists = Lists.newArrayList();
        lists.add(new Object1(1, "username1", "password1"));
        lists.add(new Object1(2, "username1", "password2"));
        lists.add(new Object1(3, "username3", "password3"));
        
//        IntSummaryStatistics count = lists.stream()
////              .filter(d -> "username1".equals(d.getUsername()))
//              .sorted(Comparator.comparing(Object1::getUsername).reversed())
//              .collect(Collectors.summarizingInt(Object1::getId));
//        System.out.println("count==="+count.getSum());
//        
//        int c = lists.stream().map(Object1::getId).reduce(0, Integer::sum);
//        System.out.println("c==="+c);
        String str = lists.stream().map(Object1::getUsername).distinct().collect(joining(","));
        System.out.println("str1==="+str);
        
        Stream.of(lists).flatMap(x->x.stream()).map(Object1::getUsername).distinct().collect(joining(","));
        System.out.println("str2==="+str);
        //Stream.of(lists) 等同于 Observable.just(lists)
        // lists.stream() 等同于 Observable.form(lists)
        
        int sum1 = lists.stream().mapToInt(Object1::getId).max().getAsInt();
        System.out.println("sum1==="+sum1);
        
        Optional<Integer> sum2 = lists.stream().map(Object1::getId).reduce(Integer::sum);
        System.out.println("sum2==="+sum2.get());
        
        Observable.from(lists).map(Object1::getUsername).distinct().subscribe(s -> {
            System.out.println("s1====" + s);
        });
        
        Observable.just(lists).flatMap(Observable::from).map(Object1::getUsername).distinct().subscribe(s -> {
            System.out.println("s2====" + s);
        });
        
//        Files.lines(Paths.get("/Developer/zxy.nexus.plist"), Charsets.UTF_8)
//            .filter(f -> f.indexOf("key") != -1)
//            .map(f -> f.split("key"))
//            .flatMap(Arrays::stream)
//            .forEach(System.out::println);
        
//        
//        Map<String,List<Object1>> list = lists.stream()
////                .filter(d -> "username1".equals(d.getUsername()))
//                .sorted(Comparator.comparing(Object1::getUsername).reversed())
////                .map(Object1::getUsername)
////                .limit(1)
//                .collect(Collectors.groupingBy(Object1::getUsername));
////        Collections.reverse(list);
        
//        System.out.println("list==="+list);
        
//        List<String> list1 = list.stream()
//                .filter(d -> "username1".equals(d.getUsername()))
//                .map(Object1::getUsername)
//                .collect(Collector.);
    }
}
