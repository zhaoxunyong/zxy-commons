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
    <mongo:db-factory id="mongoDbFactory"
                  dbname="${mongo.dbname}"
                  username="${mongo.username}"
                  password="${mongo.password}"
                  mongo-ref="mongo" />

    <mongo:mongo id="mongo"
                host="${mongo.host}"
                port="${mongo.port}">
        <mongo:options
                connections-per-host="${mongo.connectionsPerHost}"
                threads-allowed-to-block-for-connection-multiplier="${mongo.threadsAllowedToBlockForConnectionMultiplier}"
                connect-timeout="${mongo.connectTimeout}"
                max-wait-time="${mongo.maxWaitTime}"
                auto-connect-retry="${mongo.autoConnectRetry}"
                socket-keep-alive="${mongo.socketKeepAlive}"
                socket-timeout="${mongo.socketTimeout}"
                slave-ok="${mongo.slaveOk}"
                write-number="${mongo.writeNumber}"
                write-timeout="${mongo.writeTimeout}"
                write-fsync="${mongo.writeFsync}" />
    </mongo:mongo>

    <bean id="mongoTemplate" class="org.springframework.data.mongodb.core.MongoTemplate">
        <constructor-arg name="mongoDbFactory" ref="mongoDbFactory"/>
    </bean>
```

##### mongodb.properties:
```xml
	mongo.host=172.28.28.31
    mongo.port=27017
    mongo.dbname=test
    mongo.username=
    mongo.password=
    mongo.connectionsPerHost=40
    mongo.threadsAllowedToBlockForConnectionMultiplier=10
    mongo.connectTimeout=1000
    mongo.maxWaitTime=1500
    mongo.autoConnectRetry=true
    mongo.socketKeepAlive=true
    mongo.socketTimeout=1500
    mongo.slaveOk=true

    mongo.writeNumber=1
    mongo.writeTimeout=0
    mongo.writeFsync=true
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
