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
package com.zxy.commons.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

/** 
 * Mongodb test
 * 
 * <p>http://www.yl-blog.com/article/463.html
 * <p>
 * <a href="MongodbTest.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:zxy-commons-mongodb.xml" })
@SuppressWarnings("PMD")
public class MongodbTest {
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Test
    public void aCreate() {
        mongoTemplate.createCollection(User.class); 
    }
    
    @Test
    public void zDrop() {
        mongoTemplate.dropCollection(User.class);
    }
    
    @Test
    public void insert() {
        User user = new User();
        user.setData("data");
        mongoTemplate.insert(user);
    }
    
    @Test
    public void find() {
        List<User> users = mongoTemplate.findAll(User.class);
        users.forEach(user -> {
            User u = mongoTemplate.findOne(new Query(where("_id").is(user.getId())), User.class);
            System.out.println(u.getId());
        });
    }
    
    @Test
    public void delete() {
        List<User> users = mongoTemplate.findAll(User.class);
        users.forEach(user -> {
            mongoTemplate.remove(user);
        });
    }
}
