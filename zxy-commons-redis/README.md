# zxy-commons-redis

非spring项目的redis基础操作模块，包括集群与非集群操作。

### 注意：
#### 该模块主要用于非spring环境的使用，包括了集群与非集群api操作。如果是spring项目的话，请使用zxy-commons-cache模块。

### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-redis</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

### 使用说明
#### 非集群部署方式的使用(也就是redis以单机或者sentinel方式部署)：
```java
	RedisExecuter.getInstance().execute(new RedisExecution<String>() {
	
	    @Override
	    public String execute(Jedis jedis) {
	        jedis.set("key1", "222");
	        return jedis.get("key1");
	    }
	});
```
或者
```java
   String value2 = RedisExecuter.getInstance().execute(jedis -> jedis.get("key1"));
```

#### 集群部署方式的使用(也就是redis以cluster的方式部署)：
```java
	JedisCluster client = RedisClusterFactory.getInstance().getJedisCluster();
 client.set("xxx", "222");
```

请参考[DEMO](src/test/java/com/zxy/commons/redis)
