# zxy-commons-mongodb
spring mongodb的一些常用依赖。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-mongodb</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

### 使用说明
#### 该模块依赖于spring-data-mongodb，所以需要进行相关的spring配置：
##### spring配置：
```xml
    <context:property-placeholder location="classpath:mongodb.properties"/>
    <bean id="mongoClientURI" class="com.mongodb.MongoClientURI">
		<constructor-arg value="${mongodb.uri}"/>
	</bean>

	<bean id="mongoDbFactory" class="org.springframework.data.mongodb.core.SimpleMongoDbFactory">
		<constructor-arg ref="mongoClientURI" />
	</bean>
	
    <bean id="mongoTemplate" class="org.springframework.data.mongodb.core.MongoTemplate">
        <constructor-arg name="mongoDbFactory" ref="mongoDbFactory"/>
    </bean>
```

##### mongodb.properties:
请参考：[connections-connection-options](https://docs.mongodb.com/manual/reference/connection-string/#connections-connection-options)
```html
	mongodb.uri=mongodb://127.0.0.1:27017/test?connectTimeoutMS=5000&socketTimeoutMS=5000
```

##### 调用：
```java
	@Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void insert() {
        User user = new User();
        user.setData("data");
        mongoTemplate.insert(user);
    }
```

请参考[DEMO](src/test/java/com/zxy/commons/mongodb)
