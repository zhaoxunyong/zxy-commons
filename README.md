# zxy-commons公共组件说明
### 依赖说明：
项目依赖于[zxy-commons-bom](https://github.com/zhaoxunyong/zxy-commons-bom),请下载zxy-commons-bom后并执行./gradlew publishToMavenLocal安装到本地maven仓库.

### JDK版本
jdk 1.8+

### gradle版本
gradle 2.14+

### 编译
在项目根目录执行：./gradlew build

### 跳过测试用例：
如果测试用例报错，可能是某个资源连接不上，可以加上-x test跳过

### 发布到maven仓库：
#### 环境配置：
```html
	修改gradle.properties：
	#snapshot地址
	snapshotsUrl=
	#release地址
	releaseUrl=
	#用户名
	username=
	#密码
	password=
```

#### 安装到本地maven仓库
```html
	./gradlew publishToMavenLocal
```

#### 上传到maven服务器
```html
    ./gradlew publish
```

# 模块说明
## zxy-commons-codec	
加解密工具模块，包括RSA/BouncyCastle、base64、sha、md5等。
[具体请参考](zxy-commons-codec/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-codec</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-dubbo	
dubbo基础操作模块。
[具体请参考](zxy-commons-dubbo/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-dubbo</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-email	
邮件发送模块。
[具体请参考](zxy-commons-email/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-email</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-exec	
执行系统命令模块。
[具体请参考](zxy-commons-exec/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-exec</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-httpclient	
Http client工具模块，包括文件下载，post，get等方法。
[具体请参考](zxy-commons-httpclient/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-httpclient</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-json	
json处理工具，包括Object/Collection/Map与String的互转。
[具体请参考](zxy-commons-json/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-json</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-lang	
常用工具模块，包括集合、对象、字符串、id生成等功能，并对commons-lang与guava部分功能进行了二次封装。
[具体请参考](zxy-commons-lang/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-lang</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-memcache	
memcache基础操作模块。
[具体请参考](zxy-commons-memcache/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-memcache</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-modelmapper	
Model与dto自动映射模块。
[具体请参考](zxy-commons-modelmapper/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-modelmapper</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-mq	
kafka mq基础操作模块。
[具体请参考](zxy-commons-mq/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-mq</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-mybatis	
spring+mybatis基础操作模块，包括数据库读写分离与缓存。
[具体请参考](zxy-commons-mybatis/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-mybatis</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-quartz	
spring+quartz基础操作模块。
[具体请参考](zxy-commons-quartz/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-quartz</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-redis	
非spring项目的redis基础操作模块，包括集群与非集群操作。
[具体请参考](zxy-commons-redis/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-redis</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-spring	
spring基础包，包括spring的一些常用依赖。
[具体请参考](zxy-commons-spring/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-spring</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-web	
spring mvc基础包，包括spring mvc的一些常用依赖。
[具体请参考](zxy-commons-web/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-web</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-rest	
dubbo rest扩展包，包括dubbo rest的一些常用依赖。
[具体请参考](zxy-commons-rest/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-rest</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-profiler	
性能监控profiler基础包，包括性能监控profiler的一些常用依赖。
[具体请参考](zxy-commons-profiler/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-profiler</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-cache	
spring框架的redis缓存操作模块。
[具体请参考](zxy-commons-cache/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-cache</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-jetty	
内嵌web jetty常用依赖。
[具体请参考](zxy-commons-jetty/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-jetty</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-test	
junit常用依赖。
[具体请参考](zxy-commons-test/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-test</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-pool	
连接池模块。
[具体请参考](zxy-commons-pool/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-pool</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-findbugs	
findbugs依赖模块。
[具体请参考](zxy-commons-findbugs/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-findbugs</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-mongodb
spring mongodb依赖模块。
[具体请参考](zxy-commons-mongodb/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-mongodb</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

## zxy-commons-logger
日志系统的一些常用依赖。
[具体请参考](zxy-commons-logger/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-logger</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

# zxy-commons-net
常用网络传输模块。目前暂时只实现了FTP的功能。
[具体请参考](zxy-commons-net/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-net</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

# zxy-commons-apidocs
通过代码自动生成文档的依赖包。目前用swagger实现。
[具体请参考](zxy-commons-apidocs/README.md)
```xml
依赖添加：
<dependency>
    <groupId>com.zxy</groupId>
    <artifactId>zxy-commons-apidocs</artifactId>
    <version>${zxy_commons_version}</version>
</dependency>
```

# TODO:
后续会增加以下功能:
- hbase
- elasticsearch
- zeroc-ice
- seaweedfs
- fastdfs
- thrift

# 联系方式:
- QQ:
442336467
- 微信:
zhaoxunyong
- email:
zhaoxunyong@qq.com