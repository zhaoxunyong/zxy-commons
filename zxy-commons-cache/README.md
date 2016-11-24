# zxy-commons-cache
spring cache的一些常用依赖。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-cache</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```
	
### 使用说明
#### 该模块依赖于spring-data-redis，所以需要进行相关的spring配置：
##### spring配置：
```xml
    <context:property-placeholder location="classpath:redis-cache.properties" ignore-unresolvable="true" />
        
	<!-- redis 相关配置 -->
	<bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig">
		<property name="maxTotal" value="${redis.maxTotal}" />
		<property name="maxIdle" value="${redis.maxIdle}" />
		<property name="minIdle" value="${redis.minIdle}" />
		<property name="maxWaitMillis" value="${redis.maxWait}" />
		<property name="testOnBorrow" value="${redis.testOnBorrow}" />
	</bean>

	<bean id="connectionFactory"
	class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory"
	p:host-name="${redis.host}" p:port="${redis.port}" 
	p:password="${redis.password}" p:database="${redis.database}" p:pool-config-ref="poolConfig" />

	<bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
		<property name="connectionFactory" ref="connectionFactory" />
	</bean>
		
	<bean id="redisHelper" class="com.zxy.commons.cache.RedisHelper">
		<property name="redisTemplate" ref="redisTemplate" />
	</bean>
```

##### redis-cache.properties:
```xml
	# Redis settings  
	# server IP
	redis.host=127.0.0.1
	# server port
	redis.port=6379
	#redis password
	redis.password=
	#redis database
	redis.database=0
	
	#控制一个pool的最大jedis实例，默认为8
	redis.maxTotal=100
	# 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认为8
	redis.maxIdle=8
	# 控制一个pool最少有多少个状态为idle(空闲的)的jedis实例，默认为0
	redis.minIdle=0
	
	# 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间(毫秒)，则直接抛出JedisConnectionException；
	redis.maxWait=5000
	# 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
	redis.testOnBorrow=true
```

##### 调用：
```java
	@Autowired
    private RedisHelper redisHelper;
```
