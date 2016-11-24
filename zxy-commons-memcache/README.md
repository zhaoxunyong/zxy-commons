# zxy-commons-memcache
memcache基础操作模块。
### 如果其他模块需要使用这些功能时，只需要在maven中加入：
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-memcache</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

### 使用说明
- memcache.properties配置：
```java
	#多个用逗号分隔
	servers=172.28.28.50:11211
	#连接池大小，建议：1
	connectionPoolSize=1
	#超时时间，单位：毫秒
	connectTimeout=5000
```

- 使用：
```java
	MemcacheClient client = MemcacheFactory.getMemcacheClient();
    client.set("xxx", "111");
```


请参考[DEMO](src/test/java/com/zxy/commons/memcache)
